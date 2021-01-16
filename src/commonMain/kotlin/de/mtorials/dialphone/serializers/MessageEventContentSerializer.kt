package de.mtorials.dialphone.serializers

import de.mtorials.dialphone.model.mevents.DefaultEvent
import de.mtorials.dialphone.model.mevents.EventContent
import de.mtorials.dialphone.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.model.mevents.roommessage.MatrixMessageEvent
import de.mtorials.dialphone.model.mevents.roommessage.MessageEventContent
import kotlinx.serialization.json.*

class MessageEventContentSerializer() : JsonContentPolymorphicSerializer<MessageEventContent>(MessageEventContent::class) {
    override fun selectDeserializer(element: JsonElement) = when (element.jsonObject["msgtype"]?.jsonPrimitive?.content) {
        "m.text" -> MRoomMessage.TextContent.serializer()
        "m.image" -> MRoomMessage.ImageContent.serializer()
        else -> MRoomMessage.EmptyContent.serializer()
    }
}