package de.mtorials.dialphone.model

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("m.room.message")
@Serializable
class MRoomMessage(
    override val sender: String,
    @SerialName("event_id")
    override val id: String,
    @Polymorphic
    override val content: MRoomMessageContent
) : MatrixMessageEvent {

    @ContentEventType(MRoomMessage::class)
    interface MRoomMessageContent : MessageEventContent {
        val body: String
    }

    @ContentEventType(MRoomMessage::class)
    @SerialName("m.text")
    @Serializable
    data class TextContent(
        override val body: String,
        val format: String? = null,
        @SerialName("formatted_body")
        val formattedBody: String? = null
    ) : MRoomMessageContent

    @ContentEventType(MRoomMessage::class)
    @Serializable
    data class EmptyContent(override val body: String = "") : MRoomMessageContent

    @SerialName("m.image")
    @ContentEventType(MRoomMessage::class)
    @Serializable
    data class ImageContent(
        override val body: String,
        val url: String? = null,
        val file: String? = null
    ) : MRoomMessageContent

    companion object {
        const val htmlFormat = "org.matrix.custom.html"
    }
}