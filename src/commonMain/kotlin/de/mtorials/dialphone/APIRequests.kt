package de.mtorials.dialphone

import de.mtorials.dialphone.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.responses.*
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.date.*
import de.mtorials.dialphone.model.mevents.EventContent
import de.mtorials.dialphone.model.mevents.MatrixEvent
import kotlin.random.Random

class APIRequests(
    private val token: String,
    private val homeserverUrl: String,
    private val client: HttpClient
) {

    private val random = Random(getTimeMillis().toInt() * 2834)

    suspend fun discoverRooms() : RoomDiscovery = request(HttpMethod.Get, "publicRooms")
    suspend fun getJoinedRooms() : JoinedRooms = request(HttpMethod.Get, "joined_rooms")
    suspend fun getMe() : UserResponse = request(HttpMethod.Get, "account/whoami")
    suspend fun getRoomsState(id: String) : Array<MatrixStateEvent> =
        request(HttpMethod.Get, "rooms/${id}/state")
    suspend fun getUserById(id: String) : UserWithoutIDResponse? = request(HttpMethod.Get, "profile/${id}")
    suspend fun getEventByIdAndRoomId(id: String, roomId: String) : MatrixEvent =
        request(HttpMethod.Get, "rooms/${encode(roomId)}/event/${encode(id)}")
    suspend fun joinRoomWithId(id: String) : RoomResponse =
        request(HttpMethod.Post, "rooms/${encode(id)}/join")
    suspend fun leaveRoomWithId(id: String) =
        request<Any>(HttpMethod.Post, "rooms/${encode(id)}/leave")
    suspend fun getRoomIdForAlias(alias: String) : AliasExchangeResponse =
        request(HttpMethod.Get, "directory/room/${encode(alias)}")

    // Events
    suspend fun sendMessageEvent(
        eventType: String,
        content: EventContent,
        roomID: String
    ) : String {
        return request<EventResponse>(
            httpMethod = HttpMethod.Put,
            path = "rooms/${encode(roomID)}/send/$eventType/${random.nextInt()}",
            bodyValue = content
        ).id
    }

    suspend fun sendStateEvent(
        eventType: String,
        content: EventContent,
        roomID: String,
        stateKey: String
    ) : String {
        return request<EventResponse>(
            httpMethod = HttpMethod.Put,
            path = "rooms/${encode(roomID)}/state/${eventType}/${stateKey}",
            bodyValue = content
        ).id
    }
    suspend fun redactEventWithIdInRoom(roomId: String, id: String, reason: String? = null) : EventResponse =
        request(httpMethod = HttpMethod.Put, path = "rooms/${encode(roomId)}/redact/${encode(id)}/${random.nextInt()}", bodyValue = ReasonResponse(reason))

    //Profile
    suspend fun getDisplayName(userId: String) : DisplayNameResponse =
        request(HttpMethod.Get, "profile/${encode(userId)}/displayname")
    suspend fun setDisplayName(userId: String, displayName: String) : EmptyResponse =
        request(
            httpMethod = HttpMethod.Put,
            path = "profile/${encode(userId)}/displayname",
            bodyValue = object {
                val displayname: String = displayName
            }
        )
 
    private suspend inline fun <reified T> request(
        httpMethod: HttpMethod,
        path: String,
        vararg parameters: Pair<String, String> = arrayOf(),
        bodyValue: Any? = null
    ) : T {
        val newPath = homeserverUrl + DialPhone.MATRIX_PATH + path
        return client.request {
            url(newPath)
            method = httpMethod
            header("Authorization", "Bearer $token")
            header("Content-Type", "application/json")
            parameters.forEach { parameter(it.first, it.second) }
            if (bodyValue != null) body = bodyValue
        }
    }

    // Useless hopefully
    private fun encode(input: String) = input
}
