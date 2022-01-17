package de.mtorials.dialphone.core.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DisplayNameResponse (
    @SerialName("displayname")
    val displayName: String
)