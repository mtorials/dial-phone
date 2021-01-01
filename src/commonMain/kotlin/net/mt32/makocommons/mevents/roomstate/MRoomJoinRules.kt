package net.mt32.makocommons.mevents.roomstate

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.mt32.makocommons.mevents.ContentEventType
import net.mt32.makocommons.enums.JoinRule

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
    @ContentEventType(MRoomJoinRules::class)
    data class Content(
        @SerialName("join_rule")
        val joinRule: JoinRule
    ) : StateEventContent
}