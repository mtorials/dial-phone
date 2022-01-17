package de.mtorials.dialphone.core.entities.actions

import de.mtorials.dialphone.core.DialPhone

open class InvitedRoomActionsImpl(
    override val phone: DialPhone,
    override val id: String
) : de.mtorials.dialphone.core.entities.actions.InvitedRoomActions {
    override suspend fun join() : de.mtorials.dialphone.core.entities.entityfutures.RoomFuture {
        phone.requestObject.joinRoomWithId(id)
        return de.mtorials.dialphone.core.entities.entityfutures.RoomFutureImpl(id, phone)
    }
}