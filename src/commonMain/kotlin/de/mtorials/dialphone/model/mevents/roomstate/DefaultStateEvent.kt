package de.mtorials.dialphone.model.mevents.roomstate

import de.mtorials.dialphone.model.mevents.EventContent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
class DefaultStateEvent(
    override val sender: String,
    @SerialName("state_key")
    override val stateKey: String,
    @SerialName("prev_content")
    override val prevContent: DefaultStateEventContent? = null,
    override val id: String? = null,
    override val content: DefaultStateEventContent
) : MatrixStateEvent {
    @Serializable
    class DefaultStateEventContent: StateEventContent
}