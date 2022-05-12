package de.mtorials.dialphone.api.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserWithoutIDResponse(
    @SerialName("avatar_url")
    val avatarURL: String? = null,
    @SerialName("displayname")
    val displayName: String? = null,
)