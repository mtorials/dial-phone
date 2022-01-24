package de.mtorials.dialphone.core.encryption

import kotlinx.serialization.Serializable

@Serializable
data class MegolmSessions(
    val sessionId: String,
)