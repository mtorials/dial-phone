package de.mtorials.dialphone.entities

import de.mtorials.dialphone.entities.entityfutures.UserFuture

/**
 * Represents a user in a room
 */
interface Member : Entity {
    val user: UserFuture
    val displayName: String?
    val avatarUrl: String?
}