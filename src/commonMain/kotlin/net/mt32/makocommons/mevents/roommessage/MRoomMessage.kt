package net.mt32.makocommons.mevents.roommessage

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.mt32.makocommons.mevents.ContentEventType

@SerialName("m.room.message")
@Serializable
class MRoomMessage(
    override val sender: String,
    @SerialName("event_id")
    override val id: String,
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
    @SerialName("")
    @Serializable
    data class EmptyContent(override val body: String = "") : MRoomMessageContent

    @SerialName("m.image")
    @ContentEventType(MRoomMessage::class)
    @Serializable
    data class ImageContent(
        override val body: String,
        val url: String?,
        val file: String? = null
    ) : MRoomMessageContent

    companion object {
        const val htmlFormat = "org.matrix.custom.html"
    }
}