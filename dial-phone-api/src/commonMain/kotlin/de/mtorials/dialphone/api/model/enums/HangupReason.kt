package de.mtorials.dialphone.api.model.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class HangupReason {
    @SerialName("ice_failed")
    ICE_FAILED,
    @SerialName("invite_timeout")
    INVITE_TIMEOUT
}