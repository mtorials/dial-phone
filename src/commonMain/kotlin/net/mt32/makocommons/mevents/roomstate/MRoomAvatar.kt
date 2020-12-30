package net.mt32.makocommons.mevents.roomstate

import kotlinx.serialization.SerialName
import net.mt32.makocommons.mevents.ContentEventType

@SerialName("m.room.avatar")
class MRoomAvatar(
    override val sender: String,
    @SerialName("event_id")
    override val id: String?,
    override val content: Content,
    @SerialName("state_key")
    override val stateKey: String,
    @SerialName("prev_content")
    override val prevContent: Content?
) : MatrixStateEvent {
    @ContentEventType(MRoomAvatar::class)
    data class Content(
        val url: String
    ) : StateEventContent
}