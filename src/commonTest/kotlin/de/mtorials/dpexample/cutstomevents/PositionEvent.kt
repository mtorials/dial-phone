package de.mtorials.dpexample.cutstomevents

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import net.mt32.makocommons.mevents.ContentEventType
import net.mt32.makocommons.mevents.roommessage.MatrixMessageEvent
import net.mt32.makocommons.mevents.roommessage.MessageEventContent

@JsonTypeName("de.mtorials.matrix.events.position")
class PositionEvent(
    override val sender: String,
    @JsonProperty("event_id")
    override val id: String,
    override val content: Content
) : MatrixMessageEvent {
    @ContentEventType(PositionEvent::class)
    data class Content(
        val x: Int,
        val y: Int,
        val z: Int
    ) : MessageEventContent
}