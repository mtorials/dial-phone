package de.mtorials.dial.mevents

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
class EventDefault(
    sender: String,
    content: DefaultContent
) : MatrixEvent(sender, content) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    class DefaultContent : EventContent
}