package de.mtorials.dialphone.api.serialization

import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import kotlinx.serialization.json.*

class MessageEventContentSerializer() : JsonContentPolymorphicSerializer<MRoomMessage.MRoomMessageContent>(MRoomMessage.MRoomMessageContent::class) {

    override fun selectDeserializer(element: JsonElement) = when (element.jsonObject["msgtype"]?.jsonPrimitive?.content) {
        "m.text" -> MRoomMessage.TextContent.serializer()
        "m.image" -> MRoomMessage.ImageContent.serializer()
        else -> MRoomMessage.EmptyContent.serializer()
    }
}