package de.mtorials.dialphone

import net.mt32.makocommons.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.responses.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.date.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import net.mt32.makocommons.EventSerialization
import net.mt32.makocommons.mevents.EventContent
import net.mt32.makocommons.mevents.MatrixEvent
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

class APIRequests(
    val phone: DialPhone,
    subTypes: Array<KClass<out MatrixEvent>>
) {

    private val random = Random(getTimeMillis().toInt() * 2834)

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(EventSerialization.matrixFormat)
        }
    }

    init { subTypes.forEach { TODO("Subtypes") } }

    suspend fun discoverRooms() : RoomDiscovery = request(HttpMethod.Get, "publicRooms")
    suspend fun getJoinedRooms() : JoinedRooms = request(HttpMethod.Get, "joined_rooms")
    suspend fun getMe() : UserResponse = request(HttpMethod.Get, "account/whoami")
    suspend fun getRoomsState(id: String) : Array<MatrixStateEvent> =
        request<Array<MatrixEvent>>(HttpMethod.Get, "rooms/${id}/state")
            .filterIsInstance<MatrixStateEvent>().toTypedArray()
    suspend fun getUserById(id: String) : UserWithoutIDResponse? = request(HttpMethod.Get, "profile/${id}")
    suspend fun getEventByIdAndRoomId(id: String, roomId: String) : MatrixEvent =
        request(HttpMethod.Get, "rooms/${encode(roomId)}/event/${encode(id)}")
    suspend fun joinRoomWithId(id: String) : RoomResponse =
        request(HttpMethod.Post, "rooms/${encode(id)}/join")
    suspend fun getRoomIdForAlias(alias: String) : AliasExchangeResponse =
        request(HttpMethod.Get, "directory/room/${encode(alias)}")

    // Events
    @ExperimentalSerializationApi
    @InternalSerializationApi
    suspend fun sendMessageEvent(
        eventType: KClass<out MatrixEvent>,
        content: EventContent,
        roomID: String
    ) : String {
        val typename = eventType.serializer().descriptor.serialName
        return request<EventResponse>(
            httpMethod = HttpMethod.Put,
            path = "rooms/${encode(roomID)}/send/${typename}/${random.nextInt()}",
            bodyValue = content
        ).id
    }

    @ExperimentalSerializationApi
    @InternalSerializationApi
    suspend fun sendStateEvent(
        eventType: KClass<out MatrixEvent>,
        content: EventContent,
        roomID: String,
        stateKey: String
    ) : String {
        val typename = eventType.serializer().descriptor.serialName
        return request<EventResponse>(
            httpMethod = HttpMethod.Put,
            path = "rooms/${encode(roomID)}/state/${typename}/${stateKey}",
            bodyValue = content
        ).id
    }
    suspend fun redactEventWithIdInRoom(roomId: String, id: String, reason: String? = null) : EventResponse =
        request(httpMethod = HttpMethod.Put, path = "rooms/${encode(roomId)}/redact/${encode(id)}/${random.nextInt()}", bodyValue = Reason(reason))

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
        val newPath = phone.homeserverUrl + DialPhone.MATRIX_PATH + path
        return client.request {
            url(newPath)
            method = httpMethod
            header("Authorization", "Bearer ${phone.token}")
            header("Content-Type", "application/json")
            parameters.forEach { parameter(it.first, it.second) }
            if (bodyValue != null) body = bodyValue
        }
    }

    // Useless hopefully
    private fun encode(input: String) = input
}
