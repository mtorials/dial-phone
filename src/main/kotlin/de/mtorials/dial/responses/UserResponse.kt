package de.mtorials.dial.responses

import com.fasterxml.jackson.annotation.JsonProperty

class UserResponse(
    @JsonProperty("avatar_url")
    val avatarURL: String?,
    @JsonProperty("displayname")
    val displayName: String?,
    @JsonProperty("user_id")
    val id: String
)