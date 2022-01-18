package de.mtorials.dialphone.api.authentication

import kotlinx.serialization.Serializable

interface MatrixIdentifier {
    val type: String
}

@Serializable
data class MatrixUserIdIdentifier(
    val user: String,
) {
    val type: String = "m.id.user"
}