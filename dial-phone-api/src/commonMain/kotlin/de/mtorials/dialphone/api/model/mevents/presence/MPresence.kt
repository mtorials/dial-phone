package de.mtorials.dialphone.api.model.mevents.presence

import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName(MPresence.EVENT_TYPE)
@Serializable
class MPresence(
        override val sender: UserId,
        override val content: PresenceContent,
) : MatrixEvent {
    @Serializable
    data class PresenceContent(
        val presence: String,
        @SerialName("last_active_ago")
        val lastActiveAgo: Long? = null,
        @SerialName("currently_active")
        val currentlyActive: Boolean? = null
    ) : EventContent

    override fun getTypeName(): String = EVENT_TYPE

    companion object {
        const val EVENT_TYPE = "m.presence"
    }
}