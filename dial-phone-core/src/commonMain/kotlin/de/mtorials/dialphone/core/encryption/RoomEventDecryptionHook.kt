package de.mtorials.dialphone.core.encryption

import de.mtorials.dialphone.api.RoomEventHook
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.MRoomEncrypted
import de.mtorials.dialphone.api.model.enums.MessageEncryptionAlgorithm.*
import de.mtorials.dialphone.core.exceptions.UnexpectedEncryptionAlgorithmException

class RoomEventDecryptionHook(
    private val manager: EncryptionManager,
) : RoomEventHook {
    override fun manipulateEvent(event: MatrixEvent): MatrixEvent {
        if (event !is MRoomEncrypted) return event
        return when (event.content.algorithm) {
            OLM_V1_CURVE25519_AES_SHA1 -> throw UnexpectedEncryptionAlgorithmException(eventType = "room message event")
            MEGOLM_V1_AES_SHA2 -> manager.decryptMegolm(event)
        }
    }
}