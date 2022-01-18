package de.mtorials.dialphone.core.actions

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entityfutures.RoomFuture

interface InvitedRoomActions {
    val phone: DialPhone
    val id: String

    /**
     * Join the room you are invited in
     */
    suspend fun join() : RoomFuture
}