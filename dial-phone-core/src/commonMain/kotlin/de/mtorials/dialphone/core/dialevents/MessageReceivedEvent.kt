package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.Member
import de.mtorials.dialphone.core.entities.MemberImpl
import de.mtorials.dialphone.core.entities.Message
import de.mtorials.dialphone.core.entityfutures.RoomFutureImpl

class MessageReceivedEvent(
    val roomFuture: RoomFutureImpl,
    val sender: Member,
    val message: Message,
    override val id: String,
    override val phone: DialPhoneApi
) : DialEvent(phone, id) {
    constructor(roomID: String, event: MRoomMessage, phone: DialPhone) : this(
        RoomFutureImpl(roomID, phone),
        MemberImpl(
            id = event.sender,
            roomId = roomID,
            phone = phone
        ),
        Message(
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