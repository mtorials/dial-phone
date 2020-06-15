package de.mtorials.dial.mevents.room

import com.fasterxml.jackson.annotation.JsonProperty
import de.mtorials.dial.mevents.MatrixEvent

class MRoomMessage(
        override val sender: String,
        @JsonProperty("event_id")
        val id: String,
        override val content: MessageContent
) : MatrixEvent(sender, content) {
    data class MessageContent(
            @JsonProperty("msgtype")
            val msgType: String,
            val body: String
    )
}