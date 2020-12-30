package de.mtorials.dialphone.responses

import kotlinx.serialization.Serializable

@Serializable
data class ReasonResponse(
    val reason: String?
)