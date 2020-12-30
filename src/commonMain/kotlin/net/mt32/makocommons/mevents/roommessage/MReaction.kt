package net.mt32.makocommons.mevents.roommessage

import kotlinx.serialization.SerialName
import net.mt32.makocommons.mevents.ContentEventType
import net.mt32.makocommons.enums.MessageType

/**
 * Can not find documentation of this event in specification
 * Is used by the riot client for emotes
 */
@SerialName("m.reaction")
class MReaction(
    override val sender: String,
    @SerialName("event_id")
    override val id: String,
    override val content: Content
) : MatrixMessageEvent {
    @ContentEventType(MReaction::class)
    data class Content(
        @SerialName("msgtype")
        val msgType: MessageType = MessageType.TEXT,
        val body: String
    ) : MessageEventContent
}