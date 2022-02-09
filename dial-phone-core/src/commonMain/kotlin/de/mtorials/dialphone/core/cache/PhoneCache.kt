package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.core.entityfutures.RoomFuture


interface PhoneCache {
    var joinedRooms: MutableCollection<RoomFuture>
    var invitedRooms: MutableCollection<InvitedRoomActions>
    val users: MutableMap<String, User>
}