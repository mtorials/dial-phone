package de.mtorials.dialphone.encyption

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomEncrypted
import de.mtorials.dialphone.api.model.enums.MessageEncryptionAlgorithm.*
import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.core.entities.room.JoinedRoom
import de.mtorials.dialphone.encyption.exceptions.MalformedEncryptedEvent
import de.mtorials.dialphone.encyption.exceptions.UnexpectedEncryptionAlgorithmException

class RoomEventEncryption(
    private val manager: EncryptionManager,
    ) {

    fun manageIncomingRoomEvent(roomId: RoomId, event: MatrixEvent): MatrixEvent {
        if (event !is MRoomEncrypted) return event
        return when (event.content.algorithm) {
            OLM_V1_CURVE25519_AES_SHA1 -> throw UnexpectedEncryptionAlgorithmException(eventType = "room message event")
            MEGOLM_V1_AES_SHA2 -> manager.decryptEvent(roomId, event)
            null -> throw MalformedEncryptedEvent(event)
        }
    }

    fun manageOutgoingMessageEvent(room: JoinedRoom, type: String, content: EventContent) : Pair<String, EventContent> {
        if (!room.encrypted) {
            return type to content
        }
        val encryptedContent = manager.encryptEvent(room = room, content = content, type = type)
        return Pair(MRoomEncrypted.EVENT_TYPE, encryptedContent)
    }
}