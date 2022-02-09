package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.api.ids.UserId

/**
 * A matrix user
 */
interface User : Entity {
    /**
     * The username visible in the id
     */
    // val name: String?
    override val id: UserId
    val avatarURL: String?
    val displayName: String?
}