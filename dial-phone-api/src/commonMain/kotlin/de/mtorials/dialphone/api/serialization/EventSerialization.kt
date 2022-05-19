package de.mtorials.dialphone.api.serialization

import de.mtorials.dialphone.api.model.mevents.DefaultEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MReaction
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomEncrypted
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.presence.MPresence
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomRedaction
import de.mtorials.dialphone.api.model.mevents.roomstate.*
import de.mtorials.dialphone.api.model.mevents.todevice.MDummy
import de.mtorials.dialphone.api.model.mevents.todevice.MForwardedRoomKey
import de.mtorials.dialphone.api.model.mevents.todevice.MRoomKey
import de.mtorials.dialphone.api.model.mevents.todevice.MRoomKeyRequest
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object EventSerialization {

    val serializersModule = SerializersModule {
        polymorphic(MatrixEvent::class) {

            default {
                DefaultEvent.serializer()
            }

            subclass(MRoomEncrypted::class)

            // Presence
            subclass(MPresence::class)

            // Message
            subclass(MReaction::class)
            subclass(MRoomMessage::class)
            subclass(MRoomRedaction::class)

            // State
            subclass(MRoomAvatar::class)
            subclass(MRoomCreate::class)
            subclass(MRoomJoinRules::class)
            subclass(MRoomMember::class)
            subclass(MRoomName::class)
            subclass(MRoomEncryption::class)

            // to device
            subclass(MRoomKey::class)
            subclass(MRoomKeyRequest::class)
            subclass(MForwardedRoomKey::class)
            subclass(MDummy::class)

        }

        // TODO check if this is needed
        polymorphic(MatrixStateEvent::class) {
            default {
                DefaultStateEvent.serializer()
            }
            subclass(MRoomAvatar::class)
            subclass(MRoomCreate::class)
            subclass(MRoomJoinRules::class)
            subclass(MRoomMember::class)
            subclass(MRoomName::class)
            subclass(MRoomEncryption::class)
        }

//        polymorphic(EventContent::class) {
//            subclass(MRoomEncrypted.MRoomEncryptedContent::class)
//        }
    }
}