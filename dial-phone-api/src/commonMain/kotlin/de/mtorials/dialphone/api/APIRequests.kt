package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.responses.*
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.date.*
import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.requests.RoomCreateRequest
import de.mtorials.dialphone.api.requests.encryption.KeyUploadRequest
import de.mtorials.dialphone.api.responses.encryption.KeyUploadResponse
import kotlin.random.Random
import io.ktor.http.HttpMethod.Companion.Post
import io.ktor.http.HttpMethod.Companion.Get

class APIRequests(
    token: String,
    homeserverUrl: String,
    client: HttpClient,
) : MatrixClient(
    token = token,
    homeserverUrl = homeserverUrl,
    client = client,
) {

    suspend fun discoverRooms() : RoomDiscovery = request(Get, "publicRooms")
    suspend fun getJoinedRooms() : JoinedRooms = request(Get, "joined_rooms")
    suspend fun getMe() : UserResponse = request(Get, "account/whoami")
    suspend fun getRoomsState(id: RoomId) : List<MatrixStateEvent> =
        request(Get, "rooms/${id}/state")
    suspend fun getUserById(id: UserId) : UserWithoutIDResponse? = request(Get, "profile/${id}")
    suspend fun getEventByIdAndRoomId(id: String, roomId: String) : MatrixEvent =
        request(Get, "rooms/${encode(roomId)}/event/${encode(id)}")
    suspend fun joinRoomWithId(id: RoomId) : RoomResponse =
        request(Post, "rooms/${encode(id)}/join")
    suspend fun leaveRoomWithId(id: RoomId) : Unit =
        request(Post, "rooms/${encode(id)}/leave")
    suspend fun getRoomIdForAlias(alias: String) : AliasExchangeResponse =
        request(Get, "directory/room/${encode(alias)}")
    suspend fun createRoom(request: RoomCreateRequest) : RoomResponse =
        request(Post, "createRoom", bodyValue = request)
    suspend fun turnCredentials() : TurnCredentialsResponse =
        request(Get, "voip/turnServer")

    // Events
    suspend fun sendMessageEvent(
        eventType: String,
        content: EventContent,
        roomID: RoomId
    ) : EventId {
        return request<EventResponse>(
            httpMethod = HttpMethod.Put,
            path = "rooms/${encode(roomID)}/send/${eventType}/${random.nextInt()}",
            bodyValue = content,
        ).id
    }

    suspend fun sendMessageEvent(
        eventType: String,
        content: String,
        roomID: RoomId
    ) : EventId {
        return request<EventResponse>(
            httpMethod = HttpMethod.Put,
            path = "rooms/${encode(roomID)}/send/${eventType}/${random.nextInt()}",
            bodyValue = content,
        ).id
    }

    suspend fun sendStateEvent(
        eventType: String,
        content: EventContent,
        roomID: String,
        stateKey: String
    ) : EventId {
        return request<EventResponse>(
            httpMethod = HttpMethod.Put,
            path = "rooms/${encode(roomID)}/state/${eventType}/${stateKey}",
            bodyValue = content
        ).id
    }
    suspend fun redactEventWithIdInRoom(roomId: RoomId, id: EventId, reason: String? = null) : EventResponse =
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
}
