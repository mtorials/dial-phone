package de.mtorials.dialphone

import de.mtorials.dialphone.entities.User
import de.mtorials.dialphone.entities.actions.InvitedRoomActions
import de.mtorials.dialphone.entities.entityfutures.RoomFuture

class PhoneCache {
    var joinedRooms: MutableList<RoomFuture> = mutableListOf()
    var invitedRooms: MutableList<InvitedRoomActions> = mutableListOf()
    val users: MutableMap<String, User> = mutableMapOf()
}