package de.mtorials.dialphone.core.entityfutures

import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.room.JoinedRoom
import de.mtorials.dialphone.core.entities.room.JoinedRoomImpl
import de.mtorials.dialphone.api.ids.RoomId

class RoomFutureImpl(
    override val id: RoomId,
    phone: DialPhone
) : RoomFuture, RoomActionsImpl(phone, id) {
    override suspend fun receiveStateEvents(): List<MatrixStateEvent>  = phone.apiRequests.getRoomsState(id.toString())
    override suspend fun receive() : JoinedRoom =
        JoinedRoomImpl(this, receiveStateEvents())
}