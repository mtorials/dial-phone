package net.mt32.makocommons.mevents.roomstate

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.mt32.makocommons.mevents.ContentEventType
import net.mt32.makocommons.enums.Membership

@SerialName("m.room.member")
@Serializable
class MRoomMember(
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
    @ContentEventType(MRoomMember::class)
    @Serializable
    data class Content(
        val membership: Membership,
        @SerialName("avatar_url")
        val avatarURL: String?,
        @SerialName("displayname")
        val displayName: String?
    ) : StateEventContent
}