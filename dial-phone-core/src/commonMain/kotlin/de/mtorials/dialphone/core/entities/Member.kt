package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.core.entityfutures.UserFuture

/**
 * Represents a user in a room
 */
interface Member : UserFuture {
    val roomId: String
}