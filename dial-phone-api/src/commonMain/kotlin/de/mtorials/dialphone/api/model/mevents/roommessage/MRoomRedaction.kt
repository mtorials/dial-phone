package de.mtorials.dialphone.api.model.mevents.roommessage

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("m.room.redaction")
@Serializable
class MRoomRedaction(
    override val sender: UserId,
    @SerialName("event_id")
    override val id: EventId,
    override val content: Content,
    @SerialName("redacts")
    val redactionEventId: String
) : MatrixMessageEvent {
    @Serializable
    data class Content(
        val reason: String? = null
    ) : MessageEventContent
}