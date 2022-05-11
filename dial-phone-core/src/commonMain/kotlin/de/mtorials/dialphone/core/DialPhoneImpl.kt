package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.DialPhoneApiImpl
import de.mtorials.dialphone.api.Synchronizer
import de.mtorials.dialphone.api.ids.RoomAlias
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.api.responses.UserWithoutIDResponse
import de.mtorials.dialphone.core.cache.PhoneCache
import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.core.entities.UserImpl
import de.mtorials.dialphone.core.entities.room.InvitedRoom
import de.mtorials.dialphone.core.entities.room.InvitedRoomImpl
import de.mtorials.dialphone.core.entities.room.JoinedRoom
import de.mtorials.dialphone.core.entities.room.JoinedRoomImpl
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
) : DialPhone, DialPhoneApiImpl(
    token = token,
    homeserverUrl = homeserverUrl,
    ownId = ownId,
    client = client,
    initCallback = initCallback,
    coroutineScope = coroutineScope,
    deviceId = deviceId,
    format = format,
) {

    override val synchronizer = Synchronizer(
        this,
        client,
        initCallback = initCallback,
    )

    fun beforeMessageEventPublish(block: suspend (RoomId, String, EventContent) -> Pair<String, EventContent>) {
        apiRequests.beforeMessageEventPublish = block
    }

    fun beforeRoomEventListener(block: (MatrixEvent) -> MatrixEvent) {
        synchronizer.beforeRoomEvent = block
    }

    // TODO cast is unchecked, when can it fail?
    override fun addListeners(vararg listener: GenericListener<*>) {
        listener.forEach { synchronizer.addListener(it as GenericListener<DialPhoneApi>) }
    }

    override suspend fun getJoinedRooms(): List<JoinedRoom> {
        return cache.roomCache.joinedRoomIds.map { id ->
            JoinedRoomImpl(this, id, cache.roomCache.getRoomStateEvents(id))
        }
    }

    override suspend fun getInvitedRoomActions(): List<InvitedRoom>? = null

    override suspend fun getUserById(id: UserId) : User? {
        // Check cache
//        if (cache?.userCache?.containsKey(id.toString()) == true) return cache.users[id.toString()]

        // TODO impl cache
        val u : UserWithoutIDResponse = apiRequests.getUserById(id) ?: return null
        return UserImpl(
            id = id,
            displayName = u.displayName,
            avatarURL = u.avatarURL,
            phone = this
        )
    }

    override suspend fun getInvitedRoomByAlias(alias: RoomAlias): InvitedRoom =
        InvitedRoomImpl(
            this,
            apiRequests.getRoomIdForAlias(alias.toString()).roomId
        )

    override suspend fun getJoinedRoomById(id: RoomId) : JoinedRoom? {
        val filtered = getJoinedRooms().filter{ id == it.id }
        if (filtered.isEmpty()) {
            val requested = apiRequests.getJoinedRooms().roomIds.filter { id == it }
            if (requested.isEmpty()) return null
            return requested[0].run { JoinedRoomImpl(this@DialPhoneImpl, this, getStateEvents(this)) }
        }
        return filtered[0]
    }

    override suspend fun createRoom(name: String, block: RoomBuilder.() -> Unit): JoinedRoom =
        apiRequests.createRoom(RoomBuilderImpl(name).apply(block).build())
            .run { JoinedRoomImpl(this@DialPhoneImpl, this.id, getStateEvents(id)) }


    override suspend fun discoverRooms(): List<InvitedRoom> {
        return apiRequests.discoverRooms().rooms.map {
            InvitedRoomImpl(
                this,
                it.id,
                it.name,
            )
        }
    }

    // TODO better error handling
    override suspend fun getMe(): User = getUserById(this.ownId) ?: error("Can not find own user")

    private suspend fun getStateEvents(id: RoomId) : List<MatrixStateEvent> {
        val cached = cache.roomCache.getRoomStateEvents(id)
        if (cached.isNotEmpty()) return cached
        return apiRequests.getRoomsState(id)
    }
}