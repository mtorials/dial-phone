package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.core.entityfutures.RoomFuture

class InMemoryCache : PhoneCache {
    override var joinedRooms: MutableCollection<RoomFuture> = mutableListOf()
    override var invitedRooms: MutableCollection<InvitedRoomActions> = mutableListOf()
    override val users: MutableMap<String, User> = mutableMapOf()
}