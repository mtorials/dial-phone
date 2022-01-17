package de.mtorials.dialphone.core.model.mevents.roomstate

import de.mtorials.dialphone.core.model.enums.JoinRule
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("m.room.join_rules")
@Serializable
class MRoomJoinRules(
    override val sender: String,
    @SerialName("event_id")
    override val id: String? = null,
    override val content: Content,
    @SerialName("state_key")
    override val stateKey: String,
    @SerialName("prev_content")
    override val prevContent: Content? = null
) : MatrixStateEvent {
    @Serializable
    data class Content(
        @SerialName("join_rule")
        val joinRule: JoinRule
    ) : StateEventContent
}