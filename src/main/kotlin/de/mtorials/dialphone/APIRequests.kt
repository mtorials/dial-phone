package de.mtorials.dialphone

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.mtorials.dialphone.exceptions.APIException
import net.micromes.makocommons.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.responses.*
import net.micromes.makocommons.mevents.EventContent
import net.micromes.makocommons.mevents.MatrixEvent
import org.http4k.client.OkHttp
import org.http4k.core.Method
import java.net.URLEncoder
import kotlin.random.Random
import kotlin.reflect.KClass

class APIRequests(
    val phone: DialPhone,
    subTypes: Array<KClass<out MatrixEvent>>
) {
    private val mapper = jacksonObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
    private val random = Random(System.currentTimeMillis().toInt() * 2834)

    private val client = OkHttp()

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
    suspend fun joinRoomWithId(id: String) : RoomResponse =
        request(Method.POST, "rooms/${encode(id)}/join")
    suspend fun getRoomIdForAlias(alias: String) : AliasExchangeResponse =
        request(Method.GET, "directory/room/${encode(alias)}")

    // Events
    suspend fun sendMessageEvent(
        eventType: KClass<out MatrixEvent>,
        content: EventContent,
        roomID: String
    ) : String {
        val typename = eventType.annotations.filterIsInstance<JsonTypeName>()[0].value
        return request<EventResponse>(
            method = Method.PUT,
            path = "rooms/${encode(roomID)}/send/${typename}/${random.nextInt()}",
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
            body = content
        ).id
    }
    suspend fun redactEventWithIdInRoom(roomId: String, id: String, reason: String? = null) : EventResponse =
        request(method = Method.PUT, path = "rooms/${encode(roomId)}/redact/${encode(id)}/${random.nextInt()}", body = Reason(reason))

    //Profile
    suspend fun getDisplayName(userId: String) : DisplayNameResponse =
        request(Method.GET, "profile/${encode(userId)}/displayname")
    suspend fun setDisplayName(userId: String, displayName: String) : EmptyResponse =
        request(
            method = Method.PUT,
            path = "profile/${encode(userId)}/displayname",
            body = object {
                val displayname: String = displayName
            }
        )
 
    private suspend inline fun <reified T> request(
        method: Method,
        path: String,
        vararg parameters: Pair<String, String> = arrayOf(),
        body: Any? = null
    ) : T {
        val newPath = phone.homeserverUrl + DialPhone.MATRIX_PATH + path
        var request = org.http4k.core.Request(method, newPath)
            .header("Authorization", "Bearer ${phone.token}")
            .header("Content-Type", "application/json")
        for (pair in parameters) {
            request = request.query(pair.first, pair.second)
        }
        if (body != null) request = request.body(mapper.writeValueAsString(body))
        val resString = client(request).bodyString()
        try {
            return jacksonObjectMapper().readValue(resString)
        } catch(e: MissingKotlinParameterException) {
            throw jacksonObjectMapper().readValue<APIException>(resString)
        }
    }

    private fun encode(input: String) = URLEncoder.encode(input, "utf-8")
}