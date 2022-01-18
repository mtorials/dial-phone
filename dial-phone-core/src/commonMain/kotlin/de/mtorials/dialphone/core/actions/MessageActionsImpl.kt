package de.mtorials.dialphone.core.actions

import de.mtorials.dialphone.core.DialPhone

open class MessageActionsImpl(
    override val id: String,
    override val phone: DialPhone,
    override val roomId: String
) : MessageActions {
    override suspend fun redact(reason: String?) {
        phone.apiRequests.redactEventWithIdInRoom(roomId = roomId, id = id)
    }
}