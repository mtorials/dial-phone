package de.mtorials.dialphone.entities

import de.mtorials.dialphone.entities.entityfutures.UserFuture

/**
 * Represents a user in a room
 */
interface Member : UserFuture {
    val roomId: String
}