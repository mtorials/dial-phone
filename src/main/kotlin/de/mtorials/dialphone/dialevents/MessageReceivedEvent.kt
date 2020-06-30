package de.mtorials.dialphone.dialevents

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.mevents.roommessage.MRoomMessage

class MessageReceivedEvent(
    val roomFuture: RoomFutureImpl,
    val senderId: String,
    val content: MRoomMessage.MRoomMessageContent,
    override val id: String,
    override val phone: DialPhone
) : DialEvent(phone, id) {
    constructor(roomID: String, event: MRoomMessage, phone: DialPhone) : this(RoomFutureImpl(roomID, phone), event.sender, event.content, event.id, phone)
}