package net.mt32.makocommons.mevents.roomstate

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.mt32.makocommons.mevents.ContentEventType

@SerialName("m.room.name")
@Serializable
class MRoomName(
    override val sender: String,
    @SerialName("event_id")
    override val id: String?,
    @Polymorphic
    override val content: Content,
    @SerialName("state_key")
    override val stateKey: String,
    @SerialName("prev_content")
    @Polymorphic
    override val prevContent: Content?
) : MatrixStateEvent {
    @Serializable
    @ContentEventType(MRoomName::class)
    data class Content(
        val name: String
    ) : StateEventContent
}