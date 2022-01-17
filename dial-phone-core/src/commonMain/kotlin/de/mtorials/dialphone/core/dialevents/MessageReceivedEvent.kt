package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.core.DialPhoneCore
import de.mtorials.dialphone.core.model.mevents.roommessage.MRoomMessage

class MessageReceivedEvent(
    val roomFuture: de.mtorials.dialphone.core.entities.entityfutures.RoomFutureImpl,
    val sender: de.mtorials.dialphone.core.entities.Member,
    val message: de.mtorials.dialphone.core.entities.Message,
    override val id: String,
    override val phone: DialPhoneCore
) : DialEvent(phone, id) {
    constructor(roomID: String, event: MRoomMessage, phone: DialPhoneCore) : this(
        de.mtorials.dialphone.core.entities.entityfutures.RoomFutureImpl(roomID, phone),
        de.mtorials.dialphone.core.entities.MemberImpl(
            id = event.sender,
            roomId = roomID,
            phone = phone
        ),
        de.mtorials.dialphone.core.entities.Message(
            body = event.content.body,
            messageType = event.content::class,
            phone = phone,
            roomFuture = de.mtorials.dialphone.core.entities.entityfutures.RoomFutureImpl(roomID, phone),
            id = event.id,
            author = de.mtorials.dialphone.core.entities.MemberImpl(
                id = event.sender,
                roomId = roomID,
                phone = phone
            )
        ), event.id, phone)
}