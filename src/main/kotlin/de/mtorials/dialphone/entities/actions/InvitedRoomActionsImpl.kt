package de.mtorials.dialphone.entities.actions

import de.mtorials.dialphone.DialPhone

open class InvitedRoomActionsImpl(
    override val phone: DialPhone,
    override val id: String
) : InvitedRoomActions {
    override suspend fun join() {
        phone.requestObject.joinRoomWithId(id)
    }
}