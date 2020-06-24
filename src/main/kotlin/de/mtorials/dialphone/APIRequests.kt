package de.mtorials.dialphone

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import de.mtorials.dialphone.mevents.*
import de.mtorials.dialphone.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.responses.*
import java.net.URLEncoder
import kotlin.random.Random
import kotlin.reflect.KClass

class APIRequests(
    val phone: DialPhone,
    subTypes: Array<KClass<out MatrixEvent>>
) {
    private val mapper = jacksonObjectMapper()
    private val random = Random(System.currentTimeMillis().toInt() * 2834)

    init { subTypes.forEach { mapper.registerSubtypes(it.java) } }

    suspend fun discoverRooms() : RoomDiscovery = request(Method.GET, "publicRooms")
    suspend fun getJoinedRooms() : JoinedRooms = request(Method.GET, "joined_rooms")
    suspend fun getMe() : UserResponse = request(Method.GET, "account/whoami")
    suspend fun getRoomsState(id: String) : Array<MatrixStateEvent> =
        request<Array<MatrixEvent>>(Method.GET, "rooms/${id}/state")
            .filterIsInstance<MatrixStateEvent>().toTypedArray()
    suspend fun getUserById(id: String) : UserWithoutIDResponse? = request(Method.GET, "profile/${id}")
    suspend fun getEventByIdAndRoomId(id: String, roomId: String) : MatrixEvent =
        request(Method.GET, "rooms/${encode(roomId)}/event/${encode(id)}")
    suspend fun sendMessageEvent(
        eventType: KClass<out MatrixEvent>,
        content: EventContent,
        roomID: String
    ) : String {
        val typename = eventType.annotations.filterIsInstance<JsonTypeName>()[0].value
        return request<EventResponse>(
            method = Method.PUT,
            path = "rooms/${encode(roomID)}/send/${typename}/${random.nextInt()}",
            parameters = mutableListOf(),
            body = content
        ).id
    }
    suspend fun sendStateEvent(
        eventType: KClass<out MatrixEvent>,
        content: EventContent,
        roomID: String,
        stateKey: String
    ) : String {
        val typename = eventType.annotations.filterIsInstance<JsonTypeName>()[0].value
        return request<EventResponse>(
            method = Method.PUT,
            path = "rooms/${encode(roomID)}/state/${typename}/${stateKey}",
            parameters = mutableListOf(),
            body = content
        ).id
    }

    private suspend inline fun <reified T> request(
        method: Method,
        path: String,
        parameters: MutableList<Pair<String, String>> = mutableListOf(),
        body: Any? = null
    ) : T {
        val rightPath = phone.homeserverUrl + DialPhone.MATRIX_PATH + path
        val parameterEncoded = parameters.map { Pair(it.first, URLEncoder.encode(it.second, "utf-8")) }
        val request = Fuel.request(method, rightPath, parameterEncoded)
        request["Authorization"] = "Bearer ${phone.token}"
        request["Content-Type"] = "application/json"
        if (body != null) request.jsonBody(mapper.writeValueAsString(body))
        request
            .awaitStringResponseResult().third
            .fold<Unit>(
                { data -> return@request mapper.readValue(data) },
                { error ->
                    println("An error of type ${error.exception} happened: ${error.message}. Response is ${error.response.responseMessage}")
                    println(request)
                    throw RuntimeException("Problem Requesting")
                }
            )
        throw RuntimeException("Dont know")
    }

    private fun encode(input: String) = URLEncoder.encode(input, "utf-8")
}