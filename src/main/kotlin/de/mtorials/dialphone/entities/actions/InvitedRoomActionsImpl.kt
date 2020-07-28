package de.mtorials.dialphone.entities.actions

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.entityfutures.RoomFuture
import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl

open class InvitedRoomActionsImpl(
    override val phone: DialPhone,
    override val id: String
) : InvitedRoomActions {
    override suspend fun join() : RoomFuture {
        phone.requestObject.joinRoomWithId(id)
        return RoomFutureImpl(id, phone)
    }
}