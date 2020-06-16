package de.mtorials.dialphone

import de.mtorials.dialphone.entities.actions.RoomActions
import de.mtorials.dialphone.entities.entityfutures.MessageFuture
import de.mtorials.dialphone.mevents.room.MRoomMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

infix fun RoomActions.write(message: String) = GlobalScope.launch { this@write.sendTextMessage(message) }
suspend fun RoomActions.sendAndGet(message: String) =
    MessageFuture(this@sendAndGet.sendTextMessage(message), this@sendAndGet.phone)

suspend fun RoomActions.sendTextMessage(content: String) : String = this.sendEvent(
    content = MRoomMessage.MessageContent(
        msgType = "m.text",
        body = content
    )
)