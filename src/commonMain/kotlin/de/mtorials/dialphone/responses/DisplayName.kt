package de.mtorials.dialphone.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DisplayName(
    @SerialName("display_name")
    val name: String
)