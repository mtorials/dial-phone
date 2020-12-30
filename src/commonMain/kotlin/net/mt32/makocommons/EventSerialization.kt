package net.mt32.makocommons

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import net.mt32.makocommons.mevents.MatrixEvent
import net.mt32.makocommons.mevents.presence.MPresence
import net.mt32.makocommons.mevents.roommessage.MReaction
import net.mt32.makocommons.mevents.roommessage.MRoomEncrypted
import net.mt32.makocommons.mevents.roommessage.MRoomMessage
import net.mt32.makocommons.mevents.roommessage.MRoomRedaction
import net.mt32.makocommons.mevents.roomstate.*

object EventSerialization {
    val matrixFormat = Json {
        ignoreUnknownKeys = true
        classDiscriminator = "type"
        serializersModule = SerializersModule {
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
        }
    }
}