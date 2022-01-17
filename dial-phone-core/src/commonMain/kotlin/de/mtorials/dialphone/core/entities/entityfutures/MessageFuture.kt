package de.mtorials.dialphone.core.entities.entityfutures

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.model.mevents.roommessage.MRoomMessage

class MessageFuture(
    id: String,
    roomId: String,
    phone: DialPhone
) : de.mtorials.dialphone.core.entities.entityfutures.EntityFuture<de.mtorials.dialphone.core.entities.Message>, de.mtorials.dialphone.core.entities.actions.MessageActionsImpl(id, phone, roomId) {
    override suspend fun receive(): de.mtorials.dialphone.core.entities.Message {
        val event = phone.requestObject.getEventByIdAndRoomId(id, roomId)
        if (event !is MRoomMessage) throw Error("Expected other event type!")
        return de.mtorials.dialphone.core.entities.Message(
            body = event.content.body,
            messageType = event.content::class,
            phone = phone,
            roomFuture = de.mtorials.dialphone.core.entities.entityfutures.RoomFutureImpl(roomId, phone),
            id = id,
            author = de.mtorials.dialphone.core.entities.MemberImpl(
                id = event.sender,
                roomId = roomId,
                phone = phone
            )
        )
    }
}