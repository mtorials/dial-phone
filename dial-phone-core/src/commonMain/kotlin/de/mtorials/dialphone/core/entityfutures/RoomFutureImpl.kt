package de.mtorials.dialphone.core.entityfutures

import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.Room
import de.mtorials.dialphone.core.entities.RoomImpl
import de.mtorials.dialphone.core.actions.RoomActionsImpl

class RoomFutureImpl(
    override val id: String,
    phone: DialPhone
) : RoomFuture, RoomActionsImpl(phone, id) {
    override suspend fun receiveStateEvents(): List<MatrixStateEvent>  = phone.apiRequests.getRoomsState(id)
    override suspend fun receive() : Room =
        RoomImpl(this, receiveStateEvents())
}