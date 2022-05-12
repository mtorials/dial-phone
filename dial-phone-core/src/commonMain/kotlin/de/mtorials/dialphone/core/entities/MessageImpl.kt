package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.room.JoinedRoom

class MessageImpl internal constructor(
    override val phone: DialPhone,
    override val id: EventId,
    override val room: JoinedRoom,
    override val author: Member,
    override val messageType: MRoomMessage.MRoomMessageEventContentType,
    override val content: MRoomMessage.MRoomMessageContent,
) : Message {

    override suspend fun redact(reason: String?) {
        phone.apiRequests.redactEventWithIdInRoom(roomId = room.id, id = id)
    }

    override suspend fun forceUpdate() {
        TODO("Not yet implemented")
    }
}