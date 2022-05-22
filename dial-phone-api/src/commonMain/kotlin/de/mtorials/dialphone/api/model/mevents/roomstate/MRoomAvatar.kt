package de.mtorials.dialphone.api.model.mevents.roomstate

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import io.ktor.util.reflect.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName(MRoomAvatar.EVENT_TYPE)
@Serializable
class MRoomAvatar(
    override val sender: UserId,
    @SerialName("event_id")
    override val id: EventId? = null,
    @Serializable
    override val content: Content,
    @SerialName("state_key")
    override val stateKey: String,
    @SerialName("prev_content")
    @Serializable
    override val prevContent: Content? = null,
    @SerialName("origin_server_ts")
    override val originServerTs: Long? = null,
) : MatrixStateEvent {
    @Serializable
    data class Content(
        val url: String
    ) : StateEventContent

    override fun getTypeName(): String = EVENT_TYPE
    companion object {
        const val EVENT_TYPE = "m.room.avatar"
    }
}