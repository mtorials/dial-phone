package de.mtorials.dialphone.core.responses

import kotlinx.serialization.Serializable

@Serializable
data class ReasonResponse(
    val reason: String? = null
)