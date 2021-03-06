package de.mtorials.dialphone.model.mevents

import de.mtorials.dialphone.model.mevents.roommessage.MRoomEncrypted
import de.mtorials.dialphone.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.model.mevents.roommessage.MRoomRedaction
import de.mtorials.dialphone.model.mevents.presence.MPresence
import de.mtorials.dialphone.model.mevents.roomstate.*
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.serializer

object EventSerialization {

    val serializersModule = SerializersModule {
        polymorphic(MatrixEvent::class) {

            default {
                DefaultEvent.serializer()
            }

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

        polymorphic(MatrixStateEvent::class) {
            default {
                DefaultStateEvent.serializer()
            }
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