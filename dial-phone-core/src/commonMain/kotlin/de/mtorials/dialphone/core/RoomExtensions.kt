package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomName
import de.mtorials.dialphone.core.actions.RoomActions
import de.mtorials.dialphone.core.entityfutures.MessageFuture
import de.mtorials.dialphone.api.ids.eventId
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Write a message to the room
 */
suspend infix fun RoomActions.write(message: String) = this@write.sendTextMessage(message)

/**
 * rename the room
 */
suspend infix fun RoomActions.rename(name: String) = setName(name)

/**
 * Send a message and get back a message future to for example redact the message
 */
suspend fun RoomActions.sendAndGet(message: String) =
    MessageFuture(
        this@sendAndGet.sendTextMessage(message),
        this@sendAndGet.id,
        this@sendAndGet.phone
    )

/**
 * Send a Text message
 */
suspend fun RoomActions.sendTextMessage(content: String) : EventId = this.sendMessageEvent(
    content = MRoomMessage.TextContent(
        body = content
    ),
    eventType = "m.room.message"
)

/**
 * Send a HTML message
 */
suspend fun RoomActions.sendHtmlMessage(content: String, nonFormattedContent: String? = null) : EventId = this.sendMessageEvent(
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
suspend fun RoomActions.sendImageWithUrl(url: String, title: String? = null) : EventId = this.sendMessageEvent(
    content = MRoomMessage.ImageContent(
        body = title ?: "image send with dial-phone",
        url = url
    ),
    eventType = "m.room.message"
)

/**
 * Change the name of the room
 */
suspend fun RoomActions.setName(name: String) = this@setName.sendStateEvent(MRoomName.Content(name = name), "m.room.name")