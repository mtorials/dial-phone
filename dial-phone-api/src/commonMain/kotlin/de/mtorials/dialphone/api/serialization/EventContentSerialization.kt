package de.mtorials.dialphone.api.serialization

import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.api.model.mevents.todevice.MRoomKey
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object EventContentSerialization {
    val serializersModule = SerializersModule {
        polymorphic(EventContent::class) {
            subclass(MRoomKey.MRoomKeyContent::class)

            // MRoomMessageContent
            subclass(MRoomMessage.EmptyContent::class)
            subclass(MRoomMessage.ImageContent::class)
            subclass(MRoomMessage.TextContent::class)
        }

        polymorphic(MRoomMessage.MRoomMessageContent::class) {
            subclass(MRoomMessage.EmptyContent::class)
            subclass(MRoomMessage.ImageContent::class)
            subclass(MRoomMessage.TextContent::class)
        }
    }
}