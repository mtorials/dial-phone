package de.mtorials.dialphone.mevents.room

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dialphone.ContentEventType
import de.mtorials.dialphone.mevents.EventContent
import de.mtorials.dialphone.mevents.MatrixEvent
import de.mtorials.example.cutstomevents.PositionEvent

@JsonTypeName("m.room.message")
class MRoomMessage(
        override val sender: String,
        @JsonProperty("event_id")
        val id: String,
        override val content: MessageContent
) : MatrixEvent(sender, content) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ContentEventType(MRoomMessage::class)
    data class MessageContent(
        @JsonProperty("msgtype")
        val msgType: String,
        val body: String
    ) : EventContent
}