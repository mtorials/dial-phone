package de.mtorials.dialphone

import de.mtorials.dialphone.entities.actions.RoomActions
import de.mtorials.dialphone.entities.entityfutures.MessageFuture
import de.mtorials.dialphone.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.mevents.roomstate.MRoomName
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

infix fun RoomActions.write(message: String) = GlobalScope.launch { this@write.sendTextMessage(message) }
infix fun RoomActions.rename(name: String) = setName(name)

suspend fun RoomActions.sendAndGet(message: String) =
    MessageFuture(this@sendAndGet.sendTextMessage(message), this@sendAndGet.id, this@sendAndGet.phone)

suspend fun RoomActions.sendTextMessage(content: String) : String = this.sendMessageEvent(
    content = MRoomMessage.TextContent(
        body = content
    )
)

suspend fun RoomActions.sendHtmlMessage(content: String, nonFormattedContent: String? = null) : String = this.sendMessageEvent(
    content = MRoomMessage.TextContent(
        body = nonFormattedContent ?: content,
        format = MRoomMessage.htmlFormat,
        formattedBody = content
    )
)

suspend fun RoomActions.sendImageWithUrl(url: String, title: String? = null) : String = this.sendMessageEvent(
    content = MRoomMessage.ImageContent(
        body = title ?: "image send with dial-phone",
        url = url
    )
)

fun RoomActions.setName(name: String) = GlobalScope.launch {
    this@setName.sendStateEvent(MRoomName.Content(name = name))
}