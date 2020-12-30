package net.mt32.makocommons.mevents.roommessage

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.mt32.makocommons.mevents.ContentEventType
import net.mt32.makocommons.enums.MessageType

/**
 * Can not find documentation of this event in specification
 * Is used by the riot client for emotes
 */
@SerialName("m.reaction")
@Serializable
class MReaction(
    override val sender: String,
    @SerialName("event_id")
    override val id: String,
    @Polymorphic
    override val content: Content
) : MatrixMessageEvent {
    @ContentEventType(MReaction::class)
    @Serializable
    data class Content(
        @SerialName("msgtype")
        val msgType: MessageType = MessageType.TEXT,
        val body: String
    ) : MessageEventContent
}