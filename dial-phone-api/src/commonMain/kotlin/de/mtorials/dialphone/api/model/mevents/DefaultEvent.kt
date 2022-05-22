package de.mtorials.dialphone.api.model.mevents

import de.mtorials.dialphone.api.ids.UserId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DefaultEvent(
    override val sender: UserId? = null,
    override val content: DefaultContent = DefaultContent(),
) : MatrixEvent {
    @Serializable
    class DefaultContent : EventContent

    override fun getTypeName(): String = ""
}