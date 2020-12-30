package de.mtorials.dialphone.responses

import com.fasterxml.jackson.annotation.JsonProperty

class UserWithoutIDResponse(
    @JsonProperty("avatar_url")
    val avatarURL: String?,
    @JsonProperty("displayname")
    val displayName: String?
)