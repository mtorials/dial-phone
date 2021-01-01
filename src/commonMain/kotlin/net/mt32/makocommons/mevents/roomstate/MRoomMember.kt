package net.mt32.makocommons.mevents.roomstate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.mt32.makocommons.enums.Membership
import net.mt32.makocommons.mevents.ContentEventType

@SerialName("m.room.member")
@Serializable
class MRoomMember(
    override val sender: String,
    @SerialName("event_id")
    override val id: String? = null,
    override val content: Content,
    @SerialName("state_key")
    override val stateKey: String,
    @SerialName("prev_content")
    override val prevContent: Content? = null
) : MatrixStateEvent {
    @ContentEventType(MRoomMember::class)
    @Serializable
    data class Content(
        val membership: Membership,
        @SerialName("avatar_url")
        val avatarURL: String? = null,
        @SerialName("displayname")
        val displayName: String?
    ) : StateEventContent
}