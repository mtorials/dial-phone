package de.mtorials.dial.entityfutures

import de.mtorials.dial.DialPhone
import de.mtorials.dial.entities.Room
import de.mtorials.dial.entities.RoomImpl
import de.mtorials.dial.mevents.MatrixEvent

class RoomFuture(
    roomID: String,
    phone: DialPhone
) : EntityFuture<Room>(roomID, phone) {
    override suspend fun receive() : Room = RoomImpl(this, requestObject.getRoomsState(entityId))
    suspend fun sendMessage(content: String) = phone.requestObject.sendEvent(content, entityId)
    //suspend fun sendEvent(event: MatrixEvent) = phone.requestObject.send
}