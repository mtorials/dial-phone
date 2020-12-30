package net.mt32.makocommons.mevents

import kotlinx.serialization.Serializable

@Serializable
class DefaultEvent(
    override val sender: String,
    override val content: DefaultContent
) : MatrixEvent {
    @Serializable
    class DefaultContent : EventContent
}