package de.mtorials.dial.mevents.room

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dial.mevents.EventContent
import de.mtorials.dial.mevents.MatrixEvent

@JsonTypeName("m.room.message")
class MRoomMessage(
        override val sender: String,
        @JsonProperty("event_id")
        val id: String,
        override val content: MessageContent
) : MatrixEvent(sender, content) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class MessageContent(
        @JsonProperty("msgtype")
        val msgType: String,
        val body: String
    ) : EventContent
}