package de.mtorials.dialphone.core

import de.mtorials.dialphone.core.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.core.model.mevents.roomstate.MRoomName
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

infix fun de.mtorials.dialphone.core.entities.actions.RoomActions.write(message: String) = GlobalScope.launch { this@write.sendTextMessage(message) }
infix fun de.mtorials.dialphone.core.entities.actions.RoomActions.rename(name: String) = setName(name)

suspend fun de.mtorials.dialphone.core.entities.actions.RoomActions.sendAndGet(message: String) =
    de.mtorials.dialphone.core.entities.entityfutures.MessageFuture(
        this@sendAndGet.sendTextMessage(message),
        this@sendAndGet.id,
        this@sendAndGet.phone
    )

suspend fun de.mtorials.dialphone.core.entities.actions.RoomActions.sendTextMessage(content: String) : String = this.sendMessageEvent(
    content = MRoomMessage.TextContent(
        body = content
    ),
    eventType = "m.room.message"
)

suspend fun de.mtorials.dialphone.core.entities.actions.RoomActions.sendHtmlMessage(content: String, nonFormattedContent: String? = null) : String = this.sendMessageEvent(
    content = MRoomMessage.TextContent(
        body = nonFormattedContent ?: content,
        format = MRoomMessage.htmlFormat,
        formattedBody = content,
    ),
    eventType = "m.room.message"
)

suspend fun de.mtorials.dialphone.core.entities.actions.RoomActions.sendImageWithUrl(url: String, title: String? = null) : String = this.sendMessageEvent(
    content = MRoomMessage.ImageContent(
        body = title ?: "image send with dial-phone",
        url = url
    ),
    eventType = "m.room.message"
)

fun de.mtorials.dialphone.core.entities.actions.RoomActions.setName(name: String) = GlobalScope.launch {
    this@setName.sendStateEvent(MRoomName.Content(name = name), "m.room.name")
}