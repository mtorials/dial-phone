package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.Message
import de.mtorials.dialphone.core.entities.room.JoinedRoom

class MessageReceivedEvent(
    override val phone: DialPhone,
    override val id: EventId,
    val room: JoinedRoom,
    val message: Message,
) : DialEvent(phone, id) {
//    constructor(roomID: RoomId, event: MRoomMessage, phone: DialPhone) : this(
//        RoomFutureImpl(roomID, phone),
//        MemberImpl(
//            userId = event.sender,
//            roomId = roomID,
//            phone = phone
//        ),
//        MessageImpl(
//            body = event.content.body,
//            messageType = event.content::class,
//            phone = phone,
//            roomFuture = RoomFutureImpl(roomID, phone),
//            id = event.id,
//            author = MemberImpl(
//                userId = event.sender,
//                roomId = roomID,
//                phone = phone
//            )
//        ), event.id, phone)
}