package de.mtorials.dialphone.api.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthDict(
    val type: AuthType,
    val session: String,
    val identifier: Map<String, String>,
    val password: String? = null,
    val auth: String? = null,
) {
    @Serializable
    enum class AuthType {
        @SerialName("m.login.password")
        PASSWORD
    }
}