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
import de.mtorials.dialphone.api.responses.DiscoveredRoom
import de.mtorials.dialphone.api.responses.UserWithoutIDResponse
import de.mtorials.dialphone.core.actions.InvitedRoomActions
import de.mtorials.dialphone.core.actions.InvitedRoomActionsImpl
import de.mtorials.dialphone.core.cache.PhoneCache
import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.core.entities.UserImpl
import de.mtorials.dialphone.core.entityfutures.RoomFuture
import de.mtorials.dialphone.core.entityfutures.RoomFutureImpl
import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope

class DialPhoneImpl internal constructor(
    token: String,
    homeserverUrl: String,
    ownId: UserId,
    client: HttpClient,
    initCallback: suspend (DialPhoneApi) -> Unit,
    val cache: PhoneCache?,
    coroutineScope: CoroutineScope,
    deviceId: String?,
) : DialPhone, DialPhoneApiImpl(
    token = token,
    homeserverUrl = homeserverUrl,
    ownId = ownId,
    client = client,
    initCallback = initCallback,
    coroutineScope = coroutineScope,
    deviceId = deviceId,
) {

    override val synchronizer = Synchronizer(
        this, client,
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

    override suspend fun getJoinedRoomFutures(): List<RoomFuture> = apiRequests.getJoinedRooms().roomIds.map {
        RoomFutureImpl(it,this)
    }

    override suspend fun getInvitedRoomActions(): List<InvitedRoomActions>? = null

    override suspend fun getUserById(id: UserId) : User? {
        // Check cache
        if (cache?.users?.containsKey(id.toString()) == true) return cache.users[id.toString()]

        val u : UserWithoutIDResponse = apiRequests.getUserById(id.toString()) ?: return null
        return UserImpl(
            id = id,
            displayName = u.displayName,
            avatarURL = u.avatarURL,
            phone = this
        )
    }

    override suspend fun getRoomByAlias(alias: RoomAlias): InvitedRoomActions =
        InvitedRoomActionsImpl(
            this,
            apiRequests.getRoomIdForAlias(alias.toString()).roomId
        )

    override suspend fun getJoinedRoomFutureById(id: RoomId) : RoomFuture? =
        when (getJoinedRoomFutures().map { it.id }.contains(id)) {
            true -> RoomFutureImpl(id, this)
            false -> null
        }

    override suspend fun createRoom(name: String, block: RoomBuilder.() -> Unit): RoomFuture =
        apiRequests.createRoom(RoomBuilderImpl(name).apply(block).build())
            .run { RoomFutureImpl(this.id, this@DialPhoneImpl) }


    override suspend fun discoverRooms(): List<Pair<InvitedRoomActions, DiscoveredRoom>> {
        return apiRequests.discoverRooms().rooms.map { Pair(
            InvitedRoomActionsImpl(
                this,
                it.id
            ), it) }
    }
}