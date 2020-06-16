package de.mtorials.dial.mevents

@EventType("de.mtorials.default")
class EventDefault(
    sender: String,
    content: Any
) : MatrixEvent(sender, content)