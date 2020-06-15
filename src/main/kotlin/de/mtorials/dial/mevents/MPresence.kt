package de.mtorials.dial.mevents

import com.fasterxml.jackson.annotation.JsonProperty

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
    )
}