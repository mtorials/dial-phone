package de.mtorials.dialphone.api.model.mevents.roommessage

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.serialization.MessageEventContentSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("m.room.message")
@Serializable
class MRoomMessage(
    override val sender: UserId,
    @SerialName("event_id")
    override val id: EventId,
    @Serializable(with = MessageEventContentSerializer::class)
    override val content: MRoomMessageContent
) : MatrixMessageEvent {

    interface MRoomMessageContent : MessageEventContent {
        val msgType: MRoomMessageEventContentType
        val body: String
    }

    @SerialName(TEXT)
    @Serializable
    data class TextContent(
        override val body: String,
        val format: String? = null,
        @SerialName("formatted_body")
        val formattedBody: String? = null,
    ) : MRoomMessageContent {
        @SerialName("msgtype")
        override val msgType = MRoomMessageEventContentType.M_TEXT
    }

    @Serializable
    data class EmptyContent(
        override val body: String = "",
        override val msgType: MRoomMessageEventContentType = MRoomMessageEventContentType.EMPTY
    ) : MRoomMessageContent

    @SerialName(IMAGE)
    @Serializable
    data class ImageContent(
        override val body: String,
        val url: String? = null,
        val file: String? = null,
    ) : MRoomMessageContent {
        @SerialName("msgtype")
        override val msgType = MRoomMessageEventContentType.M_IMAGE
    }

    @Serializable
    enum class MRoomMessageEventContentType {
        @SerialName(IMAGE)
        M_IMAGE,
        @SerialName(TEXT)
        M_TEXT,
        @SerialName(NONE)
        EMPTY
    }

    companion object {
        const val IMAGE = "m.image"
        const val TEXT = "m.text"
        const val NONE = ""
        const val htmlFormat = "org.matrix.custom.html"
    }
}