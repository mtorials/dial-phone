package de.mtorials.dial

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.extensions.cUrlString
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import de.mtorials.dial.entities.Message
import de.mtorials.dial.entities.User
import de.mtorials.dial.responses.*
import de.mtorials.dial.mevents.MatrixEvent
import java.net.URLEncoder
import kotlin.random.Random
import kotlin.reflect.KClass

class APIRequests(
    val phone: DialPhone,
    subTypes: Array<KClass<out MatrixEvent>>
) {
    private val mapper = jacksonObjectMapper()
    private var tid = Random(System.currentTimeMillis().toInt()).nextInt()

    init { subTypes.forEach { mapper.registerSubtypes(it.java) } }

    suspend fun discoverRooms() : RoomDiscovery = request(Method.GET, "publicRooms")
    suspend fun getJoinedRooms() : JoinedRooms = request(Method.GET, "joined_rooms")
    suspend fun getMe() : UserResponse = request(Method.GET, "account/whoami")
    suspend fun getRoomsState(id: String) : Array<MatrixEvent> = request(Method.GET, "rooms/${id}/state")
    suspend fun getUserById(id: String) : User = request(Method.GET, "profile/${id}")
    suspend fun sendEvent(event: MatrixEvent, roomID: String) : String {
        return request<EventResponse>(
            method = Method.PUT,
            path = "rooms/${encode(roomID)}/send/${event::class.annotations.filterIsInstance<JsonTypeName>()[0].value}/$tid",
            parameters = mutableListOf(),
            body = event.content
        ).id
    }

    private suspend inline fun <reified T> request(
        method: Method,
        path: String,
        parameters: MutableList<Pair<String, String>> = mutableListOf(),
        body: Any? = null
    ) : T {
        val rightPath = phone.homeServerURL + DialPhone.MATRIX_PATH + path
        val parameterEncoded = parameters.map { Pair(it.first, URLEncoder.encode(it.second, "utf-8")) }
        val request = Fuel.request(method, rightPath, parameterEncoded)
        request["Authorization"] = "Bearer ${phone.token}"
        request["Content-Type"] = "application/json"
        if (body != null) request.jsonBody(mapper.writeValueAsString(body))
        tid = Random(tid).nextInt()
        println(request)
        request
            .awaitStringResponseResult().third
            .fold<Unit>(
                { data -> return@request mapper.readValue(data) },
                { error ->
                    println("An error of type ${error.exception} happened: ${error.message}. Response is ${error.response.responseMessage}")
                    throw RuntimeException("Problem Requesting")
                }
            )
        throw RuntimeException("Dont know")
    }

    private fun encode(input: String) = URLEncoder.encode(input, "utf-8")
}