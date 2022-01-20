package de.mtorials.dialphone.api.model.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RoomVisibility {
    @SerialName("public")
    PUBLIC,
    @SerialName("private")
    PRIVATE
}