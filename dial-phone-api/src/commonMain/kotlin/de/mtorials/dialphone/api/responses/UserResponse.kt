package de.mtorials.dialphone.api.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserResponse(
//    @SerialName("avatar_url")
//    val avatarURL: String? = null,
//    @SerialName("displayname")
//    val displayName: String? = null,
    @SerialName("user_id")
    val id: String,
    /**
     * Can be omitted in the case of an application service
     */
    @SerialName("device_id")
    val deviceId: String? = null,
)