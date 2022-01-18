package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.DialPhoneApiImpl
import de.mtorials.dialphone.api.Synchronizer
import de.mtorials.dialphone.api.listeners.Listener
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

class DialPhoneImpl internal constructor(
    token: String,
    homeserverUrl: String,
    listeners: List<Listener>,
    //override val commandPrefix: String,
    ownId: String,
    client: HttpClient,
    initCallback: suspend (DialPhoneApi) -> Unit,
) : DialPhone, DialPhoneApiImpl(
    token = token,
    homeserverUrl = homeserverUrl,
    listeners = listeners,
    ownId = ownId,
    client = client,
    initCallback = initCallback
) {

    override val synchronizer = Synchronizer(listeners.toMutableList(), this, client, initCallback = initCallback)

    // TODO fix impl and interface
    override val cache = object : PhoneCache {
        override var joinedRooms: MutableList<RoomFuture> = mutableListOf()
        override var invitedRooms: MutableList<InvitedRoomActions> = mutableListOf()
        override val users: MutableMap<String, User> = mutableMapOf()
    }

    override suspend fun getJoinedRoomFutures(): List<RoomFuture> = apiRequests.getJoinedRooms().roomIds.map {
        RoomFutureImpl(
            it,
            this
        )
    }
    override suspend fun getInvitedRoomActions(): List<InvitedRoomActions> = cache.invitedRooms

    //override suspend fun getJoinedRoomFutures() : List<RoomFuture> =
    //    requestObject.getJoinedRooms().roomIds.map { id -> RoomFutureImpl(id, this@DialPhoneImpl) }

    override suspend fun getUserById(id: String) : User? {
        // Check cache
        if (cache.users.containsKey(id)) return cache.users[id]

        val u : UserWithoutIDResponse = apiRequests.getUserById(id) ?: return null
        return UserImpl(
            id = id,
            displayName = u.displayName,
            avatarURL = u.avatarURL,
            phone = this
        )
    }

    override suspend fun getRoomByAlias(alias: String): InvitedRoomActions =
        InvitedRoomActionsImpl(
            this,
            apiRequests.getRoomIdForAlias(alias).roomId
        )

    override suspend fun getJoinedRoomFutureById(id: String) : RoomFuture? =
        when (getJoinedRoomFutures().map { it.id }.contains(id)) {
            true -> RoomFutureImpl(id, this)
            false -> null
        }

    override suspend fun discoverRooms(): List<Pair<InvitedRoomActions, DiscoveredRoom>> {
        return apiRequests.discoverRooms().rooms.map { Pair(
            InvitedRoomActionsImpl(
                this,
                it.id
            ), it) }
    }
}