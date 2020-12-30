package net.mt32.makocommons.mevents.roommessage

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.mt32.makocommons.mevents.ContentEventType

@SerialName("m.room.encrypted")
@Serializable
class MRoomEncrypted(
    override val sender: String,
    @SerialName("event_id")
    override val id: String,
    @Polymorphic
    override val content: Content
) : MatrixMessageEvent {
    @ContentEventType(MRoomMessage::class)
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