package de.mtorials.dialphone.api.model.mevents.roommessage

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName(MRoomRedaction.EVENT_TYPE)
@Serializable
class MRoomRedaction(
    override val sender: UserId,
    @SerialName("event_id")
    override val id: EventId,
    override val content: Content,
    @SerialName("redacts")
    val redactionEventId: String,
    @SerialName("origin_server_ts")
    override val originServerTs: Long? = null,
) : MatrixMessageEvent {
    @Serializable
    data class Content(
        val reason: String? = null
    ) : MessageEventContent

    override fun getTypeName(): String = EVENT_TYPE

    companion object {
        const val EVENT_TYPE = "m.room.redaction"
    }
}