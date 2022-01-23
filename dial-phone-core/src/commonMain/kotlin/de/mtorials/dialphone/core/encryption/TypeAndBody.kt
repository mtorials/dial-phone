package de.mtorials.dialphone.core.encryption

import kotlinx.serialization.Serializable

@Serializable
data class TypeAndBody(
    val type: Int,
    val body: String,
)