package net.mt32.makocommons.mevents.roomstate

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import net.mt32.makocommons.mevents.ContentEventType
import net.mt32.makocommons.enums.Membership

@JsonTypeName("m.room.member")
class MRoomMember(
    override val sender: String,
    @JsonProperty("event_id")
    override val id: String?,
    override val content: Content,
    @JsonProperty("state_key")
    override val stateKey: String,
    @JsonProperty("prev_content")
    override val prevContent: Content?
) : MatrixStateEvent {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ContentEventType(MRoomMember::class)
    data class Content(
        val membership: Membership,
        @JsonProperty("avatar_url")
        val avatarURL: String?,
        @JsonProperty("displayname")
        val displayName: String?
    ) : StateEventContent
}