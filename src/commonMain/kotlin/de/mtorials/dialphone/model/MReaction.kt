package de.mtorials.dialphone.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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