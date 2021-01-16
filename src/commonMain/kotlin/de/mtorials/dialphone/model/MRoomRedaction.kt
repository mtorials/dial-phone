package de.mtorials.dialphone.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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