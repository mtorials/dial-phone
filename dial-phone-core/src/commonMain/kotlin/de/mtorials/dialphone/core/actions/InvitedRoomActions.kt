package de.mtorials.dialphone.core.actions

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entityfutures.RoomFuture

interface InvitedRoomActions {
    val phone: DialPhone
    val id: RoomId

    /**
     * Join the room you are invited in
     */
    suspend fun join() : RoomFuture
}