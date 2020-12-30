package net.mt32.makocommons.mevents.roommessage

import kotlinx.serialization.SerialName
import net.mt32.makocommons.mevents.ContentEventType

@SerialName("m.room.encrypted")
class MRoomEncrypted(
    override val sender: String,
    @SerialName("event_id")
    override val id: String,
    override val content: Content
) : MatrixMessageEvent {
    @ContentEventType(MRoomMessage::class)
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