package de.mtorials.dialphone.model.mevents.space

import de.mtorials.dialphone.model.mevents.EventContent
import de.mtorials.dialphone.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.model.mevents.roomstate.StateEventContent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("m.space.parent")
@Serializable
class MSpaceParent(
    override val sender: String,
    override val stateKey: String,
    override val prevContent: EventContent? = null,
    override val id: String? = null,
    override val content: StateEventContent,
) : MatrixStateEvent {
    @Serializable
    class SpaceParentContent(
        val via: List<String> = listOf(),
        val canonical: Boolean,
    ) : StateEventContent
}