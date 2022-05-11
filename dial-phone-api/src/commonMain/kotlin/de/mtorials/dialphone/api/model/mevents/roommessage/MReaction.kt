package de.mtorials.dialphone.api.model.mevents.roommessage

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.MessageType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Can not find documentation of this event in specification
 * Is used by the riot client for emotes
 */
@SerialName("m.reaction")
@Serializable
class MReaction(
    override val sender: UserId,
    @SerialName("event_id")
    override val id: EventId,
    override val content: Content
) : MatrixMessageEvent {
    @Serializable
    data class Content(
        @SerialName("msgtype")
        val msgType: MessageType = MessageType.TEXT,
        val body: String? = null,
    ) : MessageEventContent
}