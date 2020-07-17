package de.mtorials.dialphone.mevents

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import de.mtorials.dialphone.mevents.presence.MPresence
import de.mtorials.dialphone.mevents.roommessage.MRoomEncrypted
import de.mtorials.dialphone.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.mevents.roommessage.MRoomRedaction
import de.mtorials.dialphone.mevents.roomstate.*


/**
 * Representation of a matrix event
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
    defaultImpl = DefaultEvent::class
)
@JsonSubTypes(
    JsonSubTypes.Type(value = MPresence::class),
    JsonSubTypes.Type(value = MRoomJoinRules::class),
    JsonSubTypes.Type(value = MRoomCreate::class),
    JsonSubTypes.Type(value = MRoomName::class),
    JsonSubTypes.Type(value = MRoomMember::class),
    JsonSubTypes.Type(value = MRoomMessage::class),
    JsonSubTypes.Type(value = MRoomEncrypted::class),
    JsonSubTypes.Type(value = MRoomAvatar::class),
    JsonSubTypes.Type(value = MRoomRedaction::class)
)
interface MatrixEvent {
    val sender: String
    val content: EventContent
}