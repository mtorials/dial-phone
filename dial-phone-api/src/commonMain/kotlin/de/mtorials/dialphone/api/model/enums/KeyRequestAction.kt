package de.mtorials.dialphone.api.model.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class KeyRequestAction {
    @SerialName("request")
    REQUEST,
    @SerialName("request_cancellation")
    REQUEST_CANCELLATION,
}