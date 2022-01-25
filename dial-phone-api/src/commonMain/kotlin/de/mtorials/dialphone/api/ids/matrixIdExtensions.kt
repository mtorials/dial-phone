package de.mtorials.dialphone.api.ids

fun String.roomId() = RoomId(this)
fun String.eventId() = EventId(this)
fun String.roomAlias() = RoomAlias(this)
fun String.userId() = UserId(this)