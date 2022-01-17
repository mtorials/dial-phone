package de.mtorials.dialphone.core.entities.actions

import de.mtorials.dialphone.core.DialPhone

interface InvitedRoomActions {
    val phone: DialPhone
    val id: String

    /**
     * Join the room you are invited in
     */
    suspend fun join() : de.mtorials.dialphone.core.entities.entityfutures.RoomFuture
}