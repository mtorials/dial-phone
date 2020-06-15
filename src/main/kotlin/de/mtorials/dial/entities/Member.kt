package de.mtorials.dial.entities

import de.mtorials.dial.entityfutures.UserFuture

interface Member : Entity {
    val user: UserFuture
    val displayName: String?
    val avatarUrl: String?
}