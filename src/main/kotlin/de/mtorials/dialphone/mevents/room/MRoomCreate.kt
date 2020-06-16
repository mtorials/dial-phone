package de.mtorials.dialphone.mevents.room

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dialphone.ContentEventType
import de.mtorials.dialphone.mevents.EventContent
import de.mtorials.dialphone.mevents.MatrixEvent
import de.mtorials.dialphone.mevents.MatrixStateEvent
import de.mtorials.example.cutstomevents.PositionEvent

@JsonTypeName("m.room.create")
class MRoomCreate(
    override val sender: String,
    @JsonProperty("event_id")
    override val id: String,
    override val content: Content,
    @JsonProperty("state_key")
    override val stateKey: String,
    @JsonProperty("prev_content")
    override val prevContent: Content?
) : MatrixStateEvent {
    @ContentEventType(MRoomCreate::class)
    data class Content(
        @JsonProperty("room_version")
        val roomVersion: Int,
        val creator: String
    ) : EventContent
}