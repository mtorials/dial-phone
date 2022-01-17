package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.core.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.entities.DialPhone
import de.mtorials.dialphone.entities.entities.MemberImpl
import de.mtorials.dialphone.entities.entities.Message
import de.mtorials.dialphone.entities.actions.MessageActionsImpl

class MessageFuture(
    id: String,
    roomId: String,
    phone: DialPhone
) : EntityFuture<Message>, MessageActionsImpl(id, phone, roomId) {
    override suspend fun receive(): Message {
        val event = phone.requestObject.getEventByIdAndRoomId(id, roomId)
        if (event !is MRoomMessage) throw Error("Expected other event type!")
        return Message(
            body = event.content.body,
            messageType = event.content::class,
            phone = phone,
            roomFuture = RoomFutureImpl(roomId, phone),
            id = id,
            author = MemberImpl(
                id = event.sender,
                roomId = roomId,
                phone = phone
            )
        )
    }
}