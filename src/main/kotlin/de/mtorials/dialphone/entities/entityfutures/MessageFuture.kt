package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.Message

class MessageFuture(
    entityId: String,
    phone: DialPhone
) : EntityFutureImpl<Message>(entityId, phone) {
    override suspend fun receive(): Message {
        TODO("Not yet implemented")
    }
}