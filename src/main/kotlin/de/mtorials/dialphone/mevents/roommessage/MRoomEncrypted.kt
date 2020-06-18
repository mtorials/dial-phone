package de.mtorials.dialphone.mevents.roommessage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dialphone.ContentEventType

@JsonTypeName("m.room.encrypted")
class MRoomEncrypted(
    override val sender: String,
    @JsonProperty("event_id")
    override val id: String,
    override val content: Content
) : MatrixMessageEvent {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ContentEventType(MRoomMessage::class)
    data class Content(
        @JsonProperty("sender_key")
        val senderKey: String,
        @JsonProperty("ciphertext")
        val cipherText: String,
        @JsonProperty("session_id")
        val sessionId: String,
        @JsonProperty("device_id")
        val deviceId: String,
        val algorithm: String
    ) : MessageEventContent
}