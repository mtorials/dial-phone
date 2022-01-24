package de.mtorials.dialphone.core.encryption

import de.mtorials.dialphone.api.RoomEventHook
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomEncrypted
import de.mtorials.dialphone.api.model.enums.MessageEncryptionAlgorithm.*
import de.mtorials.dialphone.core.exceptions.MalformedEncryptedEvent
import de.mtorials.dialphone.core.exceptions.OlmSessionNotFound
import de.mtorials.dialphone.core.exceptions.UnexpectedEncryptionAlgorithmException
import io.github.matrixkt.olm.Message
import io.github.matrixkt.olm.Session
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

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