package de.mtorials.example.cutstomevents

import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dialphone.mevents.EventContent
import de.mtorials.dialphone.mevents.MatrixEvent

@JsonTypeName("de.mtorials.matrix.events.position")
class PositionEvent(
    sender: String,
    content: Content
) : MatrixEvent(sender, content) {
    data class Content(
        val x: Int,
        val y: Int,
        val z: Int
    ) : EventContent
}