package de.mtorials.dialphone.api.model.mevents

import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.api.model.mevents.todevice.MRoomKey
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object EventContentSerialization {
    val serializersModule = SerializersModule {
        polymorphic(EventContent::class) {
            subclass(MRoomKey.MRoomKeyContent::class)
            // TODO do it for all

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