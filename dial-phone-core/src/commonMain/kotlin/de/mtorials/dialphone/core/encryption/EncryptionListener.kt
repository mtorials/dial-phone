package de.mtorials.dialphone.core.encryption

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.model.enums.MessageEncryptionAlgorithm
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.MRoomEncrypted
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomEncryption
import de.mtorials.dialphone.api.model.mevents.todevice.MRoomKey
import de.mtorials.dialphone.core.exceptions.encryption.MalformedEncryptedEvent
import de.mtorials.dialphone.core.exceptions.encryption.UnexpectedEncryptionAlgorithmException
import de.mtorials.dialphone.core.ids.roomId
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EncryptionListener(
    private val encryptionManager: EncryptionManager,
) : GenericListener<DialPhoneApi> {

    override fun onRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneApi, isOld: Boolean) {
        if (event !is MRoomEncryption) return
        encryptionManager.markRoomEncrypted(roomId.roomId())
    }

    override fun onToDeviceEvent(event: MatrixEvent, phone: DialPhoneApi, isOld: Boolean) {
        if (event !is MRoomEncrypted) return
        if (event.content.algorithm != MessageEncryptionAlgorithm.OLM_V1_CURVE25519_AES_SHA1)
            throw UnexpectedEncryptionAlgorithmException("to-device")
        val senderKey = event.content.senderKey ?: throw MalformedEncryptedEvent(event)
        val e = encryptionManager.decryptOlm(event)
        if (e !is MRoomKey) return
        //println("[Incoming to-device] " + Json { prettyPrint = true }.encodeToString(e))
        encryptionManager.handleKeyEvent(e, senderKey)
    }
}