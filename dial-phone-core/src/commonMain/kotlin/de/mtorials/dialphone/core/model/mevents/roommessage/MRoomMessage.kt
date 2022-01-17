package de.mtorials.dialphone.core.model.mevents.roommessage

import de.mtorials.dialphone.core.serialization.MessageEventContentSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("m.room.message")
@Serializable
class MRoomMessage(
    override val sender: String,
    @SerialName("event_id")
    override val id: String,
    @Serializable(with = MessageEventContentSerializer::class)
    override val content: MRoomMessageContent
) : MatrixMessageEvent {

    interface MRoomMessageContent : MessageEventContent {
        val msgType: String
        val body: String
    }

    @SerialName("m.text")
    @Serializable
    data class TextContent(
        override val body: String,
        val format: String? = null,
        @SerialName("formatted_body")
        val formattedBody: String? = null,
    ) : MRoomMessageContent {
        @SerialName("msgtype")
        override val msgType: String = "m.text"
    }

    @Serializable
    data class EmptyContent(override val body: String = "", override val msgType: String = "") : MRoomMessageContent

    @SerialName("m.image")
    @Serializable
    data class ImageContent(
        override val body: String,
        val url: String? = null,
        val file: String? = null,
    ) : MRoomMessageContent {
        @SerialName("msgtype")
        override val msgType: String = "m.image"
    }

    companion object {
        const val htmlFormat = "org.matrix.custom.html"
    }
}