package de.mtorials.dialphone.core.entities

class MessageImpl(

) : MessageActionsImpl(
    id = id,
    phone = phone,
    roomId = roomFuture.id
) {

    override suspend fun redact(reason: String?) {
        phone.apiRequests.redactEventWithIdInRoom(roomId = roomId.toString(), id = id.toString())
    }
}