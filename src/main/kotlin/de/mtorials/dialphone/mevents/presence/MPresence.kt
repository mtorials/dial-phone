package de.mtorials.dialphone.mevents.presence

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dialphone.mevents.EventContent
import de.mtorials.dialphone.mevents.MatrixEvent

@JsonTypeName("m.presence")
class MPresence(
        override val sender: String,
        override val content: PresenceContent
) : MatrixEvent(sender, content) {
    data class PresenceContent(
        val presence: String,
        @JsonProperty("last_active_ago")
        val lastActiveAgo: Int,
        @JsonProperty("currently_active")
        val currentlyActive: Boolean
    ) : EventContent
}