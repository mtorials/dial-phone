package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.DialPhoneApiImpl
import de.mtorials.dialphone.api.Synchronizer
import de.mtorials.dialphone.api.listeners.Listener
import de.mtorials.dialphone.api.model.enums.RoomVisibility
import de.mtorials.dialphone.api.responses.DiscoveredRoom
import de.mtorials.dialphone.api.responses.UserWithoutIDResponse
import de.mtorials.dialphone.core.actions.InvitedRoomActions
import de.mtorials.dialphone.core.actions.InvitedRoomActionsImpl
import de.mtorials.dialphone.core.cache.PhoneCache
import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.core.entities.UserImpl
import de.mtorials.dialphone.core.entityfutures.RoomFuture
import de.mtorials.dialphone.core.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.core.ids.RoomAlias
import de.mtorials.dialphone.core.ids.RoomId
import de.mtorials.dialphone.core.ids.UserId
import de.mtorials.dialphone.core.ids.roomId
import io.ktor.client.*
import kotlinx.coroutines.CoroutineDispatcher

class DialPhoneImpl internal constructor(
    token: String,
    homeserverUrl: String,
    listeners: List<Listener>,
    ownId: String,
    client: HttpClient,
    initCallback: suspend (DialPhoneApi) -> Unit,
    val cache: PhoneCache?,
    coroutineDispatcher: CoroutineDispatcher,
) : DialPhone, DialPhoneApiImpl(
    token = token,
    homeserverUrl = homeserverUrl,
    listeners = listeners,
    ownId = ownId,
    client = client,
    initCallback = initCallback,
    coroutineDispatcher = coroutineDispatcher,
) {

    // override val synchronizer = Synchronizer(listeners.toMutableList(), this, client, initCallback = initCallback)

    override suspend fun getJoinedRoomFutures(): List<RoomFuture> = synchronizer.joinedRoomIds.map {
        RoomFutureImpl(it.roomId(),this)
    }
    override suspend fun getInvitedRoomActions(): List<InvitedRoomActions> = synchronizer.invitedRoomIds.map {
        InvitedRoomActionsImpl(this, it.roomId())
    }

    //override suspend fun getJoinedRoomFutures() : List<RoomFuture> =
    //    requestObject.getJoinedRooms().roomIds.map { id -> RoomFutureImpl(id, this@DialPhoneImpl) }

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
            apiRequests.getRoomIdForAlias(alias.toString()).roomId.roomId()
        )

    override suspend fun getJoinedRoomFutureById(id: RoomId) : RoomFuture? =
        when (getJoinedRoomFutures().map { it.id }.contains(id)) {
            true -> RoomFutureImpl(id, this)
            false -> null
        }

    override suspend fun createRoom(name: String, block: RoomBuilder.() -> Unit): RoomFuture =
        apiRequests.createRoom(RoomBuilderImpl(name).apply(block).build())
            .run { RoomFutureImpl(this.id.roomId(), this@DialPhoneImpl) }


    override suspend fun discoverRooms(): List<Pair<InvitedRoomActions, DiscoveredRoom>> {
        return apiRequests.discoverRooms().rooms.map { Pair(
            InvitedRoomActionsImpl(
                this,
                it.id.roomId()
            ), it) }
    }
}