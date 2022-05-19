package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.DialPhoneApiImpl
import de.mtorials.dialphone.api.Synchronizer
import de.mtorials.dialphone.api.ids.RoomAlias
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.logging.DialPhoneLogLevel
import de.mtorials.dialphone.api.model.enums.Membership
import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.api.responses.DiscoveredRoomResponse
import de.mtorials.dialphone.core.cache.PhoneCache
import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.core.entities.UserImpl
import de.mtorials.dialphone.core.entities.room.*
import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json

class DialPhoneImpl internal constructor(
    token: String,
    homeserverUrl: String,
    ownId: UserId,
    client: HttpClient,
    initCallback: suspend (DialPhoneApi) -> Unit,
    val cache: PhoneCache,
    coroutineScope: CoroutineScope,
    deviceId: String?,
    format: Json,
    logLevel: DialPhoneLogLevel,
) : DialPhone, DialPhoneApiImpl(
    token = token,
    homeserverUrl = homeserverUrl,
    ownId = ownId,
    client = client,
    initCallback = initCallback,
    coroutineScope = coroutineScope,
    deviceId = deviceId,
    format = format,
    logLevel = logLevel,
) {

    override val synchronizer = Synchronizer(
        this,
        client,
        initCallback = initCallback,
        logLevel = logLevel,
    )

    var beforeMessageEventPublish: suspend (JoinedRoom, String, EventContent) -> Pair<String, EventContent>
            = { _, s, eventContent ->  s to eventContent }

    fun beforeRoomEventListener(block: (RoomId, MatrixEvent) -> MatrixEvent) {
        synchronizer.beforeRoomEvent = block
    }

    // TODO cast is unchecked, when can it fail?
    override fun addListeners(vararg listener: GenericListener<*>) {
        listener.forEach { synchronizer.addListener(it as GenericListener<DialPhoneApi>) }
    }

    override suspend fun getJoinedRooms(): List<JoinedRoom> {
        return cache.state.roomIds[Membership.JOIN]?.map { id ->
            JoinedRoomImpl(this, id)
        } ?: emptyList()
    }

    override suspend fun getInvitedRoomActions(): List<InvitedRoom> = cache.state.roomIds[Membership.INVITE]?.map {id ->
        InvitedRoomImpl(
            this,
            id
        )
    } ?: emptyList()


    override suspend fun getUserById(id: UserId) : User? {
        // Check cache
//        if (cache?.userCache?.containsKey(id.toString()) == true) return cache.users[id.toString()]
        return UserImpl(
            id = id,
            phone = this
        )
    }

    override suspend fun getInvitedRoomByAlias(alias: RoomAlias): InvitedRoom =
        InvitedRoomImpl(
            this,
            apiRequests.getRoomIdForAlias(alias.toString()).roomId
        )

    override suspend fun getJoinedRoomById(id: RoomId) : JoinedRoom? {
        return getJoinedRooms().filter{ id == it.id }.getOrNull(0)
    }

    override suspend fun getJoinedRoomByName(name: String, ignoreCase: Boolean): JoinedRoom? {
        if (ignoreCase) return getJoinedRooms().filter { it.name?.lowercase() == name.lowercase() }.getOrNull(0)
        return getJoinedRooms().filter { it.name == name }.getOrNull(0)
    }

    // TODO state is not up to date, because of round trip
    override suspend fun createRoom(name: String, block: RoomBuilder.() -> Unit): JoinedRoom =
        apiRequests.createRoom(RoomBuilderImpl(name).apply(block).build())
            .run { JoinedRoomImpl(this@DialPhoneImpl, this.id) }


    override suspend fun discoverRooms(): List<DiscoveredRoom> {
        return apiRequests.discoverRooms().rooms.map { r ->
            DiscoveredRoomImpl(
                this,
                r.id,
                r,
            )
        }
    }

    // TODO better error handling
    override suspend fun getMe(): User = getUserById(this.ownId) ?: error("Can not find own user")

    private suspend fun getStateEvents(id: RoomId) : List<MatrixStateEvent> {
        val cached = cache.state.getRoomStateEvents(id)
        if (cached.isNotEmpty()) return cached
        return apiRequests.getRoomsState(id)
    }
}