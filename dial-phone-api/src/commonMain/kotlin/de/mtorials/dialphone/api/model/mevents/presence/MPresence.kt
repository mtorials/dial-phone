package de.mtorials.dialphone.api.model.mevents.presence

import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("m.presence")
@Serializable
class MPresence(
        override val sender: String,
        override val content: PresenceContent
) : MatrixEvent {
    @Serializable
    data class PresenceContent(
        val presence: String,
        @SerialName("last_active_ago")
        val lastActiveAgo: Long,
        @SerialName("currently_active")
        val currentlyActive: Boolean? = null
    ) : EventContent
}