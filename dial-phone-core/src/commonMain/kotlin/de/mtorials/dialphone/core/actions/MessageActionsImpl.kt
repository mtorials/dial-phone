package de.mtorials.dialphone.core.actions

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.api.ids.RoomId

open class MessageActionsImpl(
    override val id: EventId,
    override val phone: DialPhone,
    override val roomId: RoomId
) : MessageActions {
    override suspend fun redact(reason: String?) {
        phone.apiRequests.redactEventWithIdInRoom(roomId = roomId.toString(), id = id.toString())
    }
}