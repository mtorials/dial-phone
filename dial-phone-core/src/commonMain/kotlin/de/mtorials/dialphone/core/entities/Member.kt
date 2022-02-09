package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.core.entities.room.JoinedRoom

/**
 * Represents a user in a room
 */
interface Member : User {
    val room: JoinedRoom
}