package de.mtorials.dialphone.api.model.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class JoinRule {
    @SerialName("public")
    PUBLIC,
    @SerialName("knock")
    KNOCK,
    @SerialName("invite")
    INVITE,
    @SerialName("private")
    PRIVATE,
    @SerialName("restricted")
    RESTRICTED,
}