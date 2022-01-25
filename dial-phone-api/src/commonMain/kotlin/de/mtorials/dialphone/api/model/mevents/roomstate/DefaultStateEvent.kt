package de.mtorials.dialphone.api.model.mevents.roomstate

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DefaultStateEvent(
    override val sender: UserId,
    @SerialName("state_key")
    override val stateKey: String,
    @SerialName("prev_content")
    override val prevContent: DefaultStateEventContent? = null,
    override val id: EventId? = null,
    override val content: DefaultStateEventContent
) : MatrixStateEvent {
    @Serializable
    class DefaultStateEventContent: StateEventContent
}