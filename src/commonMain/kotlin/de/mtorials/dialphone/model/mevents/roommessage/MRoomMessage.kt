package de.mtorials.dialphone.model.mevents.roommessage

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

    interface MRoomMessageContent : MessageEventContent {
        val body: String
    }

    @SerialName("m.text")
    @Serializable
    data class TextContent(
        override val body: String,
        val format: String? = null,
        @SerialName("formatted_body")
        val formattedBody: String? = null
    ) : MRoomMessageContent

    @Serializable
    data class EmptyContent(override val body: String = "") : MRoomMessageContent

    @SerialName("m.image")
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