package de.mtorials.dialphone.core.actions

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entityfutures.RoomFuture
import de.mtorials.dialphone.core.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.core.ids.RoomId

open class InvitedRoomActionsImpl(
    override val phone: DialPhone,
    override val id: RoomId
) : InvitedRoomActions {
    override suspend fun join() : RoomFuture {
        phone.apiRequests.joinRoomWithId(id.toString())
        return RoomFutureImpl(id, phone)
    }
}