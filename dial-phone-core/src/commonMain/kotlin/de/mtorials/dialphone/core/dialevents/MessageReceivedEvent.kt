package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.core.ids.EventId
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.Member
import de.mtorials.dialphone.core.entities.MemberImpl
import de.mtorials.dialphone.core.entities.Message
import de.mtorials.dialphone.core.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.core.ids.RoomId
import de.mtorials.dialphone.core.ids.eventId
import de.mtorials.dialphone.core.ids.userId

class MessageReceivedEvent(
    val roomFuture: RoomFutureImpl,
    val sender: Member,
    val message: Message,
    override val id: EventId,
    override val phone: DialPhoneApi
) : DialEvent(phone, id) {
    constructor(roomID: RoomId, event: MRoomMessage, phone: DialPhone) : this(
        RoomFutureImpl(roomID, phone),
        MemberImpl(
            userId = event.sender.userId(),
            roomId = roomID,
            phone = phone
        ),
        Message(
            body = event.content.body,
            messageType = event.content::class,
            phone = phone,
            roomFuture = RoomFutureImpl(roomID, phone),
            id = event.id.eventId(),
            author = MemberImpl(
                userId = event.sender.userId(),
                roomId = roomID,
                phone = phone
            )
        ), event.id.eventId(), phone)
}