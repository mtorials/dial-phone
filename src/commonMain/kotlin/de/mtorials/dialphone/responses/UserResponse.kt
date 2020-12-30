package de.mtorials.dialphone.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserResponse(
    @SerialName("avatar_url")
    val avatarURL: String?,
    @SerialName("displayname")
    val displayName: String?,
    @SerialName("user_id")
    val id: String
)