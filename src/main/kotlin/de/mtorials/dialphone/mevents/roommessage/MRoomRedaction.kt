package de.mtorials.dialphone.mevents.roommessage

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import de.mtorials.dialphone.ContentEventType

@JsonTypeName("m.room.redaction")
class MRoomRedaction(
    override val sender: String,
    @JsonProperty("event_id")
    override val id: String,
    override val content: Content,
    @JsonProperty("redacts")
    val redactionEventId: String
) : MatrixMessageEvent {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ContentEventType(MRoomRedaction::class)
    data class Content(
        val reason: String?
    ) : MessageEventContent
}