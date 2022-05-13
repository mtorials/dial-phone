package de.mtorials.dialphone.encyption

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.MRoomEncrypted
import de.mtorials.dialphone.api.model.enums.MessageEncryptionAlgorithm.*
import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.encyption.exceptions.MalformedEncryptedEvent
import de.mtorials.dialphone.encyption.exceptions.UnexpectedEncryptionAlgorithmException

class RoomEventEncryption(
    private val manager: EncryptionManager,
) {
    fun manageIncomingRoomEvent(event: MatrixEvent): MatrixEvent {
        if (event !is MRoomEncrypted) return event
        return when (event.content.algorithm) {
            OLM_V1_CURVE25519_AES_SHA1 -> throw UnexpectedEncryptionAlgorithmException(eventType = "room message event")
            MEGOLM_V1_AES_SHA2 -> manager.decryptMegolm(event)
            null -> throw MalformedEncryptedEvent(event)
        }
    }

    suspend fun manageOutgoingMessageEvent(roomId: RoomId, type: String, content: EventContent) : Pair<String, EventContent> {
        if (!manager.checkIfRoomEncrypted(roomId)) {
            return Pair(type, content)
        }
        val encryptedContent = manager.encryptMegolm(content = content, roomId = roomId, type = type)
        return Pair("m.room.encrypted", encryptedContent)
    }
}