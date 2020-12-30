package net.mt32.makocommons.mevents.roomstate

import kotlinx.serialization.SerialName
import net.mt32.makocommons.mevents.ContentEventType
import net.mt32.makocommons.enums.Membership

@SerialName("m.room.member")
class MRoomMember(
    override val sender: String,
    @SerialName("event_id")
    override val id: String?,
    override val content: Content,
    @SerialName("state_key")
    override val stateKey: String,
    @SerialName("prev_content")
    override val prevContent: Content?
) : MatrixStateEvent {
    @ContentEventType(MRoomMember::class)
    data class Content(
        val membership: Membership,
        @SerialName("avatar_url")
        val avatarURL: String?,
        @SerialName("displayname")
        val displayName: String?
    ) : StateEventContent
}