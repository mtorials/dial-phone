package de.mtorials.dialphone.api.model.mevents

import kotlinx.serialization.Serializable

@Serializable
data class TypeAndBody(
    val type: Int,
    val body: String,
)