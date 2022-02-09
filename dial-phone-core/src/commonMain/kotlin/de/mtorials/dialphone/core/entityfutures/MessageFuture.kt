package de.mtorials.dialphone.core.entityfutures

import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.MemberImpl
import de.mtorials.dialphone.core.entities.MessageImpl
import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.RoomId

class MessageFuture(
    id: EventId,
    roomId: RoomId,
    phone: DialPhone
) : EntityFuture<MessageImpl>, MessageActionsImpl(id, phone, roomId) {
    override suspend fun receive(): MessageImpl {
        val event = phone.apiRequests.getEventByIdAndRoomId(id.toString(), roomId.toString())
        if (event !is MRoomMessage) throw Error("Expected other event type!")
        return MessageImpl(
            body = event.content.body,
            messageType = event.content::class,
            phone = phone,
            roomFuture = RoomFutureImpl(roomId, phone),
            id = id,
            author = MemberImpl(
                userId = event.sender,
                roomId = roomId,
                phone = phone
            )
        )
    }
}