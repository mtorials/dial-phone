package net.mt32.makocommons.mevents.roommessage

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.mt32.makocommons.mevents.ContentEventType

@SerialName("m.room.redaction")
@Serializable
class MRoomRedaction(
    override val sender: String,
    @SerialName("event_id")
    override val id: String,
    override val content: Content,
    @SerialName("redacts")
    val redactionEventId: String
) : MatrixMessageEvent {
    @ContentEventType(MRoomRedaction::class)
    @Serializable
    data class Content(
        val reason: String? = null
    ) : MessageEventContent
}