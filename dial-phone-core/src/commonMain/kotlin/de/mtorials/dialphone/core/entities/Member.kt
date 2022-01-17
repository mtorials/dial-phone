package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.core.entities.entityfutures.UserFuture

/**
 * Represents a user in a room
 */
interface Member : de.mtorials.dialphone.core.entities.entityfutures.UserFuture {
    val roomId: String
}