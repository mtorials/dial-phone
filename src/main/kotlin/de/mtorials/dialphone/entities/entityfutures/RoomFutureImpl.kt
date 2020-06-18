package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.Room
import de.mtorials.dialphone.entities.RoomImpl
import de.mtorials.dialphone.entities.actions.RoomActionsImpl
import de.mtorials.dialphone.mevents.roomstate.MatrixStateEvent

class RoomFutureImpl(
    val entityId: String,
    phone: DialPhone
) : EntityFuture<Room>, RoomFuture, RoomActionsImpl(phone, entityId) {
    override suspend fun receiveStateEvents(): Array<MatrixStateEvent>  = phone.requestObject.getRoomsState(entityId)
    override suspend fun receive() : Room = RoomImpl(this, receiveStateEvents())
}