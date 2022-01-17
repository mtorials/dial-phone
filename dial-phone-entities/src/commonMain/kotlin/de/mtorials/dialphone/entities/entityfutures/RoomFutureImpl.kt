package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.core.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.entities.DialPhone
import de.mtorials.dialphone.entities.entities.Room
import de.mtorials.dialphone.entities.entities.RoomImpl
import de.mtorials.dialphone.entities.actions.RoomActionsImpl

class RoomFutureImpl(
    override val id: String,
    phone: DialPhone
) : RoomFuture, RoomActionsImpl(phone, id) {
    override suspend fun receiveStateEvents(): List<MatrixStateEvent>  = phone.requestObject.getRoomsState(id)
    override suspend fun receive() : Room =
        RoomImpl(this, receiveStateEvents())
}