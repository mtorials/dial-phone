package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomName
import de.mtorials.dialphone.core.actions.RoomActions
import de.mtorials.dialphone.core.entityfutures.MessageFuture
import de.mtorials.dialphone.core.ids.eventId
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Write a message to the room
 */
infix fun RoomActions.write(message: String) = GlobalScope.launch { this@write.sendTextMessage(message) }

/**
 * rename the room
 */
infix fun RoomActions.rename(name: String) = setName(name)

/**
 * Send a message and get back a message future to for example redact the message
 */
suspend fun RoomActions.sendAndGet(message: String) =
    MessageFuture(
        this@sendAndGet.sendTextMessage(message).eventId(),
        this@sendAndGet.id,
        this@sendAndGet.phone
    )

/**
 * Send a Text message
 */
suspend fun RoomActions.sendTextMessage(content: String) : String = this.sendMessageEvent(
    content = MRoomMessage.TextContent(
        body = content
    ),
    eventType = "m.room.message"
)

/**
 * Send a HTML message
 */
suspend fun RoomActions.sendHtmlMessage(content: String, nonFormattedContent: String? = null) : String = this.sendMessageEvent(
    content = MRoomMessage.TextContent(
        body = nonFormattedContent ?: content,
        format = MRoomMessage.htmlFormat,
        formattedBody = content,
    ),
    eventType = "m.room.message"
)

/**
 * Send an image
 */
suspend fun RoomActions.sendImageWithUrl(url: String, title: String? = null) : String = this.sendMessageEvent(
    content = MRoomMessage.ImageContent(
        body = title ?: "image send with dial-phone",
        url = url
    ),
    eventType = "m.room.message"
)

/**
 * Change the name of the room
 */
fun RoomActions.setName(name: String) = GlobalScope.launch {
    this@setName.sendStateEvent(MRoomName.Content(name = name), "m.room.name")
}