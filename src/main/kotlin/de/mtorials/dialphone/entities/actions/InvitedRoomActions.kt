package de.mtorials.dialphone.entities.actions

import de.mtorials.dialphone.DialPhone

interface InvitedRoomActions {
    val phone: DialPhone
    val id: String

    /**
     * Join the room you are invited in
     */
    suspend fun join()
}