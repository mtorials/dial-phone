package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.Synchronizer
import de.mtorials.dialphone.api.responses.DiscoveredRoom
import de.mtorials.dialphone.api.responses.UserWithoutIDResponse
import de.mtorials.dialphone.core.actions.InvitedRoomActions
import de.mtorials.dialphone.core.actions.InvitedRoomActionsImpl
import de.mtorials.dialphone.core.cache.PhoneCache
import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.core.entities.UserImpl
import de.mtorials.dialphone.core.entityfutures.RoomFuture
import de.mtorials.dialphone.core.entityfutures.RoomFutureImpl

class DialPhoneImpl internal constructor(dialPhoneApi: DialPhoneApi) : DialPhone, DialPhoneApi by dialPhoneApi {

    override val synchronizer = Synchronizer(listeners.toMutableList(), this as DialPhone, client, initCallback = initCallback)

    // TODO fix impl and interface
    override val cache = object : PhoneCache {
        override var joinedRooms: MutableList<RoomFuture> = mutableListOf()
        override var invitedRooms: MutableList<InvitedRoomActions> = mutableListOf()
        override val users: MutableMap<String, User> = mutableMapOf()
    }

    override suspend fun getJoinedRoomFutures(): List<RoomFuture> = requestObject.getJoinedRooms().roomIds.map {
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

        val u : UserWithoutIDResponse = requestObject.getUserById(id) ?: return null
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
            requestObject.getRoomIdForAlias(alias).roomId
        )

    override suspend fun getJoinedRoomFutureById(id: String) : RoomFuture? =
        when (getJoinedRoomFutures().map { it.id }.contains(id)) {
            true -> RoomFutureImpl(id, this)
            false -> null
        }

    override suspend fun discoverRooms(): List<Pair<InvitedRoomActions, DiscoveredRoom>> {
        return requestObject.discoverRooms().rooms.map { Pair(
            InvitedRoomActionsImpl(
                this,
                it.id
            ), it) }
    }
}