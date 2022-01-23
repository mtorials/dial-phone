package de.mtorials.dialphone.core.encryption

data class MegolmSession(
    val roomId: String,
    val senderKey: String,
    val sessionId: String,
)