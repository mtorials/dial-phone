package de.mtorials.dialphone.mevents.room

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dialphone.ContentEventType
import de.mtorials.dialphone.mevents.EventContent
import de.mtorials.dialphone.mevents.MatrixEvent
import de.mtorials.dialphone.mevents.MatrixMessageEvent
import de.mtorials.dialphone.mevents.MatrixStateEvent

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
        val msgType: String = "m.text",
        val body: String
    ) : EventContent
}