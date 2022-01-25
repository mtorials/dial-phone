package de.mtorials.dialphone.core.entityfutures

import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.actions.MessageActionsImpl
import de.mtorials.dialphone.core.entities.MemberImpl
import de.mtorials.dialphone.core.entities.Message
import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.userId

class MessageFuture(
    id: EventId,
    roomId: RoomId,
    phone: DialPhone
) : EntityFuture<Message>, MessageActionsImpl(id, phone, roomId) {
    override suspend fun receive(): Message {
        val event = phone.apiRequests.getEventByIdAndRoomId(id.toString(), roomId.toString())
        if (event !is MRoomMessage) throw Error("Expected other event type!")
        return Message(
            body = event.content.body,
            messageType = event.content::class,
            phone = phone,
            roomFuture = RoomFutureImpl(roomId, phone),
            id = id,
            author = MemberImpl(
                userId = event.sender.userId(),
                roomId = roomId,
                phone = phone
            )
        )
    }
}