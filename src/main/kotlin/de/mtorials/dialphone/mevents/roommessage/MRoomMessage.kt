package de.mtorials.dialphone.mevents.roommessage

import com.fasterxml.jackson.annotation.*
import de.mtorials.dialphone.ContentEventType

@JsonTypeName("m.room.message")
class MRoomMessage(
    override val sender: String,
    @JsonProperty("event_id")
    override val id: String,
    override val content: MRoomMessageContent
) : MatrixMessageEvent {

    @ContentEventType(MRoomMessage::class)
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "msgtype", defaultImpl = EmptyContent::class)
    @JsonSubTypes(
        JsonSubTypes.Type(value = TextContent::class),
        JsonSubTypes.Type(value = ImageContent::class)
    )
    interface MRoomMessageContent : MessageEventContent {
        val body: String
    }

    @ContentEventType(MRoomMessage::class)
    @JsonTypeName("m.text")
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class TextContent(
        override val body: String,
        val format: String? = null,
        @JsonProperty("formatted_body")
        val formattedBody: String? = null
    ) : MRoomMessageContent

    @ContentEventType(MRoomMessage::class)
    @JsonTypeName("")
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class EmptyContent(override val body: String = "") : MRoomMessageContent

    @JsonTypeName("m.image")
    @ContentEventType(MRoomMessage::class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class ImageContent(
        override val body: String,
        val url: String?,
        val file: String? = null
    ) : MRoomMessageContent

    companion object {
        const val htmlFormat = "org.matrix.custom.html"
    }
}