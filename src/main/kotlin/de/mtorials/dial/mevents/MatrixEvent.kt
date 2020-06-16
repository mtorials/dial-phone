package de.mtorials.dial.mevents

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver
import de.mtorials.dial.mevents.room.*


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", defaultImpl = EventDefault::class)
//@JsonSubTypes(
//    JsonSubTypes.Type(name = "m.presence", value = MPresence::class),
//    JsonSubTypes.Type(name = "m.room.join.rules", value = MRoomJoinRules::class),
//    JsonSubTypes.Type(name = "m.room.create", value = MRoomCreate::class),
//    JsonSubTypes.Type(name = "m.room.member", value = MRoomMember::class),
//    JsonSubTypes.Type(name = "m.room.message", value = MRoomMessage::class),
//    JsonSubTypes.Type(name = "m.room.avatar", value = MRoomAvatar::class),
//    JsonSubTypes.Type(name = "m.room.name", value = MRoomName::class)
//)
@JsonSubTypes(
    JsonSubTypes.Type(value = MPresence::class),
    JsonSubTypes.Type(value = MRoomJoinRules::class),
    JsonSubTypes.Type(value = MRoomCreate::class),
    JsonSubTypes.Type(value = MRoomName::class),
    JsonSubTypes.Type(value = MRoomMember::class),
    JsonSubTypes.Type(value = MRoomMessage::class),
    JsonSubTypes.Type(value = MRoomAvatar::class)
)
abstract class MatrixEvent (
    open val sender: String,
    open val content: Any
)