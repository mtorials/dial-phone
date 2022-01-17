package de.mtorials.dialphone.entities.actions

import de.mtorials.dialphone.entities.DialPhone

open class MessageActionsImpl(
    override val id: String,
    override val phone: DialPhone,
    override val roomId: String
) : MessageActions {
    override suspend fun redact(reason: String?) {
        phone.requestObject.redactEventWithIdInRoom(roomId = roomId, id = id)
    }
}