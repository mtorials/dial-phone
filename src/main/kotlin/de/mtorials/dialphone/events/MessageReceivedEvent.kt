package de.mtorials.dialphone.events

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.entityfutures.RoomFuture
import de.mtorials.dialphone.mevents.room.MRoomMessage

class MessageReceivedEvent(
    val roomFuture: RoomFuture,
    val senderId: String,
    val content: MRoomMessage.MessageContent,
    override val id: String,
    override val phone: DialPhone
) : Event(id, phone) {
    constructor(roomID: String, event: MRoomMessage, phone: DialPhone) : this(RoomFuture(roomID, phone), event.sender, event.content, event.id, phone)
}