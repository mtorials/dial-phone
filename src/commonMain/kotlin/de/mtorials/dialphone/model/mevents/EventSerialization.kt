package de.mtorials.dialphone.model.mevents

import de.mtorials.dialphone.model.mevents.roommessage.MRoomEncrypted
import de.mtorials.dialphone.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.model.mevents.roommessage.MRoomRedaction
import de.mtorials.dialphone.model.mevents.presence.MPresence
import de.mtorials.dialphone.model.mevents.roomstate.*
import de.mtorials.dialphone.serializers.MessageEventContentSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object EventSerialization {
    val serializersModule = SerializersModule {
        polymorphic(MatrixEvent::class) {

            // Presence
            subclass(MPresence::class)

            // Message
            subclass(MReaction::class)
            subclass(MRoomEncrypted::class)
            subclass(MRoomMessage::class)
            subclass(MRoomRedaction::class)

            // State
            subclass(MRoomAvatar::class)
            subclass(MRoomCreate::class)
            subclass(MRoomJoinRules::class)
            subclass(MRoomMember::class)
            subclass(MRoomName::class)
        }

        polymorphic(MRoomMessage.MRoomMessageContent::class) {

            subclass(MRoomMessage.EmptyContent::class)
            subclass(MRoomMessage.ImageContent::class)
            subclass(MRoomMessage.TextContent::class)
        }
    }
}