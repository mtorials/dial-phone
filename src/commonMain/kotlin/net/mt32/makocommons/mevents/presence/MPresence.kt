package net.mt32.makocommons.mevents.presence

import kotlinx.serialization.SerialName
import net.mt32.makocommons.mevents.ContentEventType
import net.mt32.makocommons.mevents.EventContent
import net.mt32.makocommons.mevents.MatrixEvent

@SerialName("m.presence")
class MPresence(
        override val sender: String,
        override val content: PresenceContent
) : MatrixEvent {
    @ContentEventType(MPresence::class)
    data class PresenceContent(
        val presence: String,
        @SerialName("last_active_ago")
        val lastActiveAgo: Long,
        @SerialName("currently_active")
        val currentlyActive: Boolean
    ) : EventContent
}