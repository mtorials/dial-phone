package de.mtorials.dialphone.mevents

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
class EventDefault(
    override val sender: String,
    override val content: DefaultContent
) : MatrixEvent {
    @JsonIgnoreProperties(ignoreUnknown = true)
    class DefaultContent : EventContent
}