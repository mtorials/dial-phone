package de.mtorials.dialphone.model.mevents.roommessage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("m.room.encrypted")
@Serializable
class MRoomEncrypted(
    override val sender: String,
    @SerialName("event_id")
    override val id: String,
    override val content: Content
) : MatrixMessageEvent {
    @Serializable
    data class Content(
        @SerialName("sender_key")
        val senderKey: String? = null,
        @SerialName("ciphertext")
        val cipherText: String,
        @SerialName("session_id")
        val sessionId: String,
        @SerialName("device_id")
        val deviceId: String,
        val algorithm: String
    ) : MessageEventContent
}