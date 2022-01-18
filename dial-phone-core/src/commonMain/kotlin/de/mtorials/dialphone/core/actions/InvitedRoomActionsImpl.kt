package de.mtorials.dialphone.core.actions

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entityfutures.RoomFuture
import de.mtorials.dialphone.core.entityfutures.RoomFutureImpl

open class InvitedRoomActionsImpl(
    override val phone: DialPhone,
    override val id: String
) : InvitedRoomActions {
    override suspend fun join() : RoomFuture {
        phone.requestObject.joinRoomWithId(id)
        return RoomFutureImpl(id, phone)
    }
}