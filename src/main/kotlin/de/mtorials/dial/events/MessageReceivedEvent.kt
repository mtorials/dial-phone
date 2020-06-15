package de.mtorials.dial.events

import de.mtorials.dial.DialPhone
import de.mtorials.dial.entityfutures.RoomFuture
import de.mtorials.dial.mevents.room.MRoomMessage

class MessageReceivedEvent(
    val roomFuture: RoomFuture,
    val senderId: String,
    val content: MRoomMessage.MessageContent,
    override val id: String,
    override val phone: DialPhone
) : Event(id, phone) {
    constructor(roomID: String, event: MRoomMessage, phone: DialPhone) : this(RoomFuture(roomID, phone), event.sender, event.content, event.id, phone)
}