package de.mtorials.dialphone.core.entities.entityfutures

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.model.mevents.roomstate.MatrixStateEvent

class RoomFutureImpl(
    override val id: String,
    phone: DialPhone
) : de.mtorials.dialphone.core.entities.entityfutures.RoomFuture, de.mtorials.dialphone.core.entities.actions.RoomActionsImpl(phone, id) {
    override suspend fun receiveStateEvents(): List<MatrixStateEvent>  = phone.requestObject.getRoomsState(id)
    override suspend fun receive() : de.mtorials.dialphone.core.entities.Room =
        de.mtorials.dialphone.core.entities.RoomImpl(this, receiveStateEvents())
}