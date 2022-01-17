package de.mtorials.dialphone.core

import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.core.entities.actions.InvitedRoomActions
import de.mtorials.dialphone.core.entities.entityfutures.RoomFuture

class PhoneCache {
    var joinedRooms: MutableList<de.mtorials.dialphone.core.entities.entityfutures.RoomFuture> = mutableListOf()
    var invitedRooms: MutableList<de.mtorials.dialphone.core.entities.actions.InvitedRoomActions> = mutableListOf()
    val users: MutableMap<String, de.mtorials.dialphone.core.entities.User> = mutableMapOf()
}