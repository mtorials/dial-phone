package de.mtorials.dialphone.api.model

import de.mtorials.dialphone.api.ids.RoomId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestedKeyInfo(
    val algorithm: String,
    @SerialName("room_id")
    val roomId: RoomId,
    @SerialName("sender_key")
    val senderKey: String,
    @SerialName("session_id")
    val sessionId: String,
)