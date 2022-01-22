package de.mtorials.dialphone.api.model.mevents.todevice

import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("m.dummy")
data class MDummy(
    override val content: MDummyContent,
) : MatrixEvent {
    override val sender: String? = null
    @Serializable
    class MDummyContent : EventContent
}