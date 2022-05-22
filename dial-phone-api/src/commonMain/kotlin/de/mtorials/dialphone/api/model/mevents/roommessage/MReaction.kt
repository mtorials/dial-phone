package de.mtorials.dialphone.api.model.mevents.roommessage

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.MessageType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Can not find documentation of this event in specification
 * Is used by the riot client for emotes
 * Maybe not anymore? see m.sticker
 */
@SerialName(MReaction.EVENT_TYPE)
@Serializable
class MReaction(
    override val sender: UserId,
    @SerialName("event_id")
    override val id: EventId,
    override val content: Content,
    @SerialName("origin_server_ts")
    override val originServerTs: Long? = null,
) : MatrixMessageEvent {
    @Serializable
    data class Content(
        @SerialName("msgtype")
        val msgType: MessageType = MessageType.TEXT,
        val body: String? = null,
    ) : MessageEventContent

    override fun getTypeName(): String = EVENT_TYPE

    companion object {
        const val EVENT_TYPE = "m.reaction"
    }
}