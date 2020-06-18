package de.mtorials.dialphone.events

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.mevents.roommessage.MRoomMessage

class MessageReceivedEvent(
    val roomFuture: RoomFutureImpl,
    val senderId: String,
    val content: MRoomMessage.Content,
    override val id: String,
    override val phone: DialPhone
) : DialEvents(id, phone) {
    constructor(roomID: String, event: MRoomMessage, phone: DialPhone) : this(RoomFutureImpl(roomID, phone), event.sender, event.content, event.id, phone)
}