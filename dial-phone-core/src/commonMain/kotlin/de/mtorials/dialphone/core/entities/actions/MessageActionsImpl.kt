package de.mtorials.dialphone.core.entities.actions

import de.mtorials.dialphone.core.DialPhone

open class MessageActionsImpl(
    override val id: String,
    override val phone: DialPhone,
    override val roomId: String
) : de.mtorials.dialphone.core.entities.actions.MessageActions {
    override suspend fun redact(reason: String?) {
        phone.requestObject.redactEventWithIdInRoom(roomId = roomId, id = id)
    }
}