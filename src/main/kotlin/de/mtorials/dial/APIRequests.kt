package de.mtorials.dial

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import de.mtorials.dial.entities.Message
import de.mtorials.dial.entities.User
import de.mtorials.dial.responses.*
import de.mtorials.dial.mevents.MatrixEvent
import java.net.URLEncoder

class APIRequests(
    val phone: DialPhone
) {
    private val mapper = jacksonObjectMapper()

    suspend fun discoverRooms() : RoomDiscovery = request(Method.GET, "publicRooms")
    suspend fun getJoinedRooms() : JoinedRooms = request(Method.GET, "joined_rooms")
    suspend fun getMe() : UserResponse = request(Method.GET, "account/whoami")
    suspend fun getRoomsState(id: String) : Array<MatrixEvent> = request(Method.GET, "rooms/${id}/state")
    suspend fun getUserById(id: String) : User = request(Method.GET, "profile/${id}")
    suspend fun sendPlainMessage(content: String, roomID: String) : String = request<EventResponse>(
        method = Method.POST,
        path = "rooms/${encode(roomID)}/send/${MESSAGE_EVENT_TYPE}",
        parameters = mutableListOf(),
        body = Message(
            body = content
        )).id

    private suspend inline fun <reified T> request(
        method: Method,
        path: String,
        parameters: MutableList<Pair<String, String>> = mutableListOf(),
        body: Any? = null
    ) : T {
        val rightPath = phone.homeServerURL + DialPhone.MATRIX_PATH + path
        val parameterEncoded = parameters.map { Pair(it.first, URLEncoder.encode(it.second, "utf-8")) }
        val request = Fuel.request(method, rightPath, parameterEncoded)
        request["Content-Type"] = "application/json"
        request["Authorization"] = "Bearer ${phone.token}"
        if (body != null) request.body(mapper.writeValueAsString(body))
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

    companion object {
        private const val MESSAGE_EVENT_TYPE = "m.room.message"
    }
}