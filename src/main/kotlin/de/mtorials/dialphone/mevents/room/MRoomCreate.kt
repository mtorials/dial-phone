package de.mtorials.dialphone.mevents.room

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dialphone.ContentEventType
import de.mtorials.dialphone.mevents.EventContent
import de.mtorials.dialphone.mevents.MatrixEvent
import de.mtorials.example.cutstomevents.PositionEvent

@JsonTypeName("m.room.create")
class MRoomCreate(
    sender: String,
    @JsonProperty("event_id")
    val id: String,
    override val content: Content
) : MatrixEvent(sender, content) {
    @ContentEventType(MRoomCreate::class)
    data class Content(
        @JsonProperty("room_version")
        val roomVersion: Int,
        val creator: String
    ) : EventContent
}