package de.mtorials.dialphone.api.model.mevents.roomstate

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName(MRoomName.EVENT_TYPE)
@Serializable
class MRoomName(
    override val sender: UserId,
    @SerialName("event_id")
    override val id: EventId? = null,
    override val content: Content,
    @SerialName("state_key")
    override val stateKey: String,
    @SerialName("prev_content")
    override val prevContent: Content? = null,
    @SerialName("origin_server_ts")
    override val originServerTs: Long? = null,
) : MatrixStateEvent {
    @Serializable
    data class Content(
        val name: String
    ) : StateEventContent

    override fun getTypeName(): String = EVENT_TYPE

    companion object {
        const val EVENT_TYPE = "m.room.name"
    }
}