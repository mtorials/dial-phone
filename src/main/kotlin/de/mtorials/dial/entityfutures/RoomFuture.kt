package de.mtorials.dial.entityfutures

import de.mtorials.dial.DialPhone
import de.mtorials.dial.entities.Room
import de.mtorials.dial.entities.RoomImpl

class RoomFuture(
    roomID: String,
    phone: DialPhone
) : EntityFuture<Room>(roomID, phone) {
    override suspend fun receive() : Room = RoomImpl(this, requestObject.getRoomsState(entityId))
    suspend fun sendMessage(content: String) = phone.requestObject.sendPlainMessage(content, entityId)
}