package de.mtorials.dialphone.entities

import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * A matrix user
 */
@JsonDeserialize(`as` = UserImpl::class)
interface User : Entity {
    /**
     * The username visible in the id
     */
    val name: String?
    val avatarURL: String?
    val displayName: String?
}