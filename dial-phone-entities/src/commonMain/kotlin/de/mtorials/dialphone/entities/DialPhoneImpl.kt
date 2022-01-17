package de.mtorials.dialphone.entities

import de.mtorials.dialphone.core.DialPhoneCore
import de.mtorials.dialphone.core.PhoneCache
import de.mtorials.dialphone.core.responses.DiscoveredRoom
import de.mtorials.dialphone.core.responses.UserWithoutIDResponse
import de.mtorials.dialphone.entities.actions.InvitedRoomActions
import de.mtorials.dialphone.entities.actions.InvitedRoomActionsImpl
import de.mtorials.dialphone.entities.entities.User
import de.mtorials.dialphone.entities.entities.UserImpl
import de.mtorials.dialphone.entities.entityfutures.RoomFuture
import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl

class DialPhoneImpl internal constructor(dialPhoneCore: DialPhoneCore) : DialPhone, DialPhoneCore by dialPhoneCore {

    override val cache = PhoneCache()

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