package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.Message
import de.mtorials.dialphone.mevents.roommessage.MRoomMessage

class MessageFuture(
    val id: String,
    val roomId: String,
    val phone: DialPhone
) : EntityFuture<Message> {
    override suspend fun receive(): Message {
        val event = phone.requestObject.getEventByIdAndRoomId(id, roomId)
        if (event !is MRoomMessage) throw Error("Expected other event type!")
        return Message(event.content.body, event.content::class)
    }
}