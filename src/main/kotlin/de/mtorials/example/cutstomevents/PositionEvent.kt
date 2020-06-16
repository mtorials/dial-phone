package de.mtorials.example.cutstomevents

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dialphone.ContentEventType
import de.mtorials.dialphone.mevents.EventContent
import de.mtorials.dialphone.mevents.MatrixEvent
import de.mtorials.dialphone.mevents.MatrixMessageEvent

@JsonTypeName("de.mtorials.matrix.events.position")
class PositionEvent(
    override val sender: String,
    override val content: Content,
    @JsonProperty("event_id")
    override val id: String
) : MatrixMessageEvent {
    @ContentEventType(PositionEvent::class)
    data class Content(
        val x: Int,
        val y: Int,
        val z: Int
    ) : EventContent
}