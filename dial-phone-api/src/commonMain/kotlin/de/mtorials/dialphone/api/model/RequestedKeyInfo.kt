package de.mtorials.dialphone.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestedKeyInfo(
    val algorithm: String,
    @SerialName("room_id")
    val roomId: String,
    @SerialName("sender_key")
    val senderKey: String,
    @SerialName("session_id")
    val sessionId: String,
)