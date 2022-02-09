package de.mtorials.dialphone.core.entities.room

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.core.entities.Entity

interface InvitedRoom : Entity {
    override val id: RoomId
    val name: String

    /**
     * Join the room you are invited in
     */
    suspend fun join() : JoinedRoom
}