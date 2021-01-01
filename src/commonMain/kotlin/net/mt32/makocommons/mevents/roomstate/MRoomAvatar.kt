package net.mt32.makocommons.mevents.roomstate

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.mt32.makocommons.mevents.ContentEventType

@SerialName("m.room.avatar")
@Serializable
class MRoomAvatar(
    override val sender: String,
    @SerialName("event_id")
    override val id: String? = null,
    @Serializable
    override val content: Content,
    @SerialName("state_key")
    override val stateKey: String,
    @SerialName("prev_content")
    @Serializable
    override val prevContent: Content? = null
) : MatrixStateEvent {
    @ContentEventType(MRoomAvatar::class)
    @Serializable
    data class Content(
        val url: String
    ) : StateEventContent
}