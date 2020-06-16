package de.mtorials.dialphone.mevents.room

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dialphone.enums.Membership
import de.mtorials.dialphone.mevents.EventContent
import de.mtorials.dialphone.mevents.MatrixEvent

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
    ) : EventContent
}