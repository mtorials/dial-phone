package de.mtorials.dialexample.cutstomevents

import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dial.mevents.EventContent
import de.mtorials.dial.mevents.MatrixEvent

@JsonTypeName("de.mtorials.matrix.events.position")
class PositionEvent(
    sender: String,
    content: Content
) : MatrixEvent(sender, content) {
    class Content (
        x: Int,
        y: Int,
        z: Int
    ) : EventContent
}