package de.mtorials.dial.actions

import de.mtorials.dial.APIRequests
import de.mtorials.dial.DialPhone
import de.mtorials.dial.entities.Room
import de.mtorials.dial.entities.RoomImpl

class RoomFuture(
    roomID: String,
    phone: DialPhone
) : DialFuture<Room>(roomID, phone) {
    override suspend fun complete() : Room = RoomImpl(this, requestObject.getRoomsState(entityId))
    suspend fun sendMessage(content: String) = phone.requestObject.sendPlainMessage(content, entityId)
}