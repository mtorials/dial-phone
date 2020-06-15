package de.mtorials.dial.entities

import com.fasterxml.jackson.annotation.JsonProperty
import de.mtorials.dial.DialPhone

class UserImpl(
    @JsonProperty("avatar_url")
    override val avatarURL: String?,
    @JsonProperty("displayname")
    override val displayName: String?,
    @JsonProperty("user_id")
    override val id: String,
    override val phone: DialPhone
) : User {
    override val name: String? = id
}