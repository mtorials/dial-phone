package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.Room
import de.mtorials.dialphone.entities.RoomImpl
import de.mtorials.dialphone.entities.actions.RoomActionsImpl
import de.mtorials.dialphone.model.mevents.roomstate.MatrixStateEvent

class RoomFutureImpl(
    override val id: String,
    phone: DialPhone
) : RoomFuture, RoomActionsImpl(phone, id) {
    override suspend fun receiveStateEvents(): List<MatrixStateEvent>  = phone.requestObject.getRoomsState(id)
    override suspend fun receive() : Room = RoomImpl(this, receiveStateEvents())
}