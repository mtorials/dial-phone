package de.mtorials.dialphone.core.entities

/**
 * A matrix user
 */
interface User : Entity {
    /**
     * The username visible in the id
     */
    val name: String?
    val avatarURL: String?
    val displayName: String?
}