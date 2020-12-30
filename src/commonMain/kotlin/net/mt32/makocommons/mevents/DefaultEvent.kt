package net.mt32.makocommons.mevents

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

class DefaultEvent(
    override val sender: String,
    override val content: DefaultContent
) : MatrixEvent {
    @JsonIgnoreProperties(ignoreUnknown = true)
    class DefaultContent : EventContent
}