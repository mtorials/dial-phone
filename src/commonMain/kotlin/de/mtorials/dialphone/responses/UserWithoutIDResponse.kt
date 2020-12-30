package de.mtorials.dialphone.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserWithoutIDResponse(
    @SerialName("avatar_url")
    val avatarURL: String?,
    @SerialName("displayname")
    val displayName: String?
)