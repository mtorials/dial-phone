package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.Message

class MessageFuture(
    val id: String,
    val roomId: String,
    val phone: DialPhone
) : EntityFuture<Message> {
    override suspend fun receive(): Message {
        TODO("Not yet implemented")
    }
}