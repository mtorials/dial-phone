package de.mtorials.dialphone.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("m.presence")
@Serializable
class MPresence(
        override val sender: String,
        override val content: PresenceContent
) : MatrixEvent {
    @ContentEventType(MPresence::class)
    @Serializable
    data class PresenceContent(
        val presence: String,
        @SerialName("last_active_ago")
        val lastActiveAgo: Long,
        @SerialName("currently_active")
        val currentlyActive: Boolean
    ) : EventContent
}