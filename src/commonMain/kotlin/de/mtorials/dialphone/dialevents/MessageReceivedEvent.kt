package de.mtorials.dialphone.dialevents

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.Member
import de.mtorials.dialphone.entities.MemberImpl
import de.mtorials.dialphone.entities.Message
import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.model.mevents.roommessage.MRoomMessage

class MessageReceivedEvent(
    val roomFuture: RoomFutureImpl,
    val sender: Member,
    val message: Message,
    override val id: String,
    override val phone: DialPhone
) : DialEvent(phone, id) {
    constructor(roomID: String, event: MRoomMessage, phone: DialPhone) : this(RoomFutureImpl(roomID, phone), MemberImpl(
        id = event.sender,
        roomId = roomID,
        phone = phone
    ), Message(
        body = event.content.body,
        messageType = event.content::class,
        phone = phone,
        roomFuture = RoomFutureImpl(roomID, phone),
        id = event.id,
        author = MemberImpl(
            id = event.sender,
            roomId = roomID,
            phone = phone
        )
    ), event.id, phone)
}