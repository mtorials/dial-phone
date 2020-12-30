package net.mt32.makocommons.mevents.roommessage

import kotlinx.serialization.SerialName
import net.mt32.makocommons.mevents.ContentEventType

@SerialName("m.room.redaction")
class MRoomRedaction(
    override val sender: String,
    @SerialName("event_id")
    override val id: String,
    override val content: Content,
    @SerialName("redacts")
    val redactionEventId: String
) : MatrixMessageEvent {
    @ContentEventType(MRoomRedaction::class)
    data class Content(
        val reason: String?
    ) : MessageEventContent
}