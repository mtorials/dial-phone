package de.mtorials.dial.mevents.room

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dial.mevents.EventType
import de.mtorials.dial.mevents.MatrixEvent

@JsonTypeName("m.room.avatar")
class MRoomAvatar(
    sender: String,
    @JsonProperty("event_id")
    val id: String,
    override val content: Content
) : MatrixEvent(sender, content) {
    data class Content(
        val url: String
    )
}