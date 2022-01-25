package de.mtorials.dialphone.api.model.mevents

import kotlinx.serialization.Serializable

@Serializable
class DefaultEvent(
    override val sender: String? = null,
    override val content: DefaultContent = DefaultContent(),
) : MatrixEvent {
    @Serializable
    class DefaultContent : EventContent
}