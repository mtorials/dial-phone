package de.mtorials.dial.mevents.room

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dial.enums.Membership
import de.mtorials.dial.mevents.MatrixEvent

@JsonTypeName("m.room.member")
class MRoomMember(
    sender: String,
    @JsonProperty("event_id")
    val id: String,
    override val content: Content
) : MatrixEvent(sender, content) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Content(
        val membership: Membership,
        @JsonProperty("avatar_url")
        val avatarURL: String?,
        @JsonProperty("displayname")
        val displayName: String?
    )
}