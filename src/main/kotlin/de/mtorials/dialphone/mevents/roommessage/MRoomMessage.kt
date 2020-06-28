package de.mtorials.dialphone.mevents.roommessage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dialphone.ContentEventType
import de.mtorials.dialphone.enums.MessageType

@JsonTypeName("m.room.message")
class MRoomMessage(
    override val sender: String,
    @JsonProperty("event_id")
    override val id: String,
    override val content: Content
) : MatrixMessageEvent {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ContentEventType(MRoomMessage::class)
    data class Content(
        @JsonProperty("msgtype")
        val msgType: MessageType = MessageType.TEXT,
        val body: String,
        val format: String? = null,
        @JsonProperty("formatted_body")
        val formattedBody: String? = null
    ) : MessageEventContent

    companion object {
        const val htmlFormat = "org.matrix.custom.html"
    }
}