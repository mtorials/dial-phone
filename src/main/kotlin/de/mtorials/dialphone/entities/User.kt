package de.mtorials.dialphone.entities

import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(`as` = UserImpl::class)
interface User : Entity {
    val name: String?
    val avatarURL: String?
    val displayName: String?
}