package de.mtorials.dialexample.cutstomevents

import de.mtorials.dial.mevents.MatrixEvent

class PositionEvent(
    sender: String,
    content: Content
) : MatrixEvent(sender, content) {
    class Content (
        x: Int,
        y: Int,
        z: Int
    )
}