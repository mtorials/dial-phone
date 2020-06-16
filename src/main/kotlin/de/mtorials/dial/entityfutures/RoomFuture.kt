package de.mtorials.dial.entityfutures

import de.mtorials.dial.DialPhone
import de.mtorials.dial.entities.Room
import de.mtorials.dial.entities.RoomImpl
import de.mtorials.dial.mevents.MatrixEvent
import de.mtorials.dial.mevents.room.MRoomMessage

class RoomFuture(
    roomID: String,
    phone: DialPhone
) : EntityFuture<Room>(roomID, phone) {
    override suspend fun receive() : Room = RoomImpl(this, requestObject.getRoomsState(entityId))
    suspend fun sendMessage(content: String) = phone.requestObject.sendEvent(MRoomMessage(
        sender = phone.ownId,
        id = "",
        content = MRoomMessage.MessageContent(
            msgType = "m.text",
            body = content
        )
    ), entityId)
    suspend fun sendEvent(event: MatrixEvent) = phone.requestObject.sendEvent(event, entityId)
}