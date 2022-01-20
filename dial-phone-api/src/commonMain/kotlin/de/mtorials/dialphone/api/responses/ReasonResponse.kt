package de.mtorials.dialphone.api.responses

import kotlinx.serialization.Serializable

@Serializable
data class ReasonResponse(
    val reason: String? = null
)