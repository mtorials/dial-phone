package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomName
import de.mtorials.dialphone.core.entities.MemberImpl
import de.mtorials.dialphone.core.entities.Message
import de.mtorials.dialphone.core.entities.MessageImpl
import de.mtorials.dialphone.core.entities.room.JoinedRoom

/**
 * Write a message to the room
 */
suspend infix fun JoinedRoom.write(message: String) = this@write.sendTextMessage(message)

/**
 * rename the room
 */
suspend infix fun JoinedRoom.rename(name: String) = setName(name)

suspend fun JoinedRoom.sendTextMessage(content: String) = sendMRoomMessageEvent(MRoomMessage.TextContent(body = content))

/**
 * Send a HTML message
 */
suspend fun JoinedRoom.sendHtmlMessage(content: String, nonFormattedContent: String? = null) = this.sendMRoomMessageEvent(
    content = MRoomMessage.TextContent(
        body = nonFormattedContent ?: content,
        format = MRoomMessage.htmlFormat,
        formattedBody = content,
    )
)

/**
 * Send an image
 */
suspend fun JoinedRoom.sendImageWithUrl(url: String, title: String? = null) = this.sendMRoomMessageEvent(
    content = MRoomMessage.ImageContent(
        body = title ?: "image send with dial-phone",
        url = url
    )
)

/**
 * Change the name of the room
 */
suspend fun JoinedRoom.setName(name: String) = this@setName.sendStateEvent(MRoomName.Content(
    name = name
), "m.room.name")