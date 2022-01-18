package de.mtorials.dialphone.core.entityfutures

import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.MemberImpl
import de.mtorials.dialphone.core.entities.Message
import de.mtorials.dialphone.core.actions.MessageActionsImpl

class MessageFuture(
    id: String,
    roomId: String,
    phone: DialPhone
) : EntityFuture<Message>, MessageActionsImpl(id, phone, roomId) {
    override suspend fun receive(): Message {
        val event = phone.apiRequests.getEventByIdAndRoomId(id, roomId)
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