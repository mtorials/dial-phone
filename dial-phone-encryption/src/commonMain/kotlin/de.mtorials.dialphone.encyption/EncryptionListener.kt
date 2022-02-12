package de.mtorials.dialphone.encyption

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.model.enums.MessageEncryptionAlgorithm
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.MRoomEncrypted
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomEncryption
import de.mtorials.dialphone.api.model.mevents.todevice.MRoomKey
import de.mtorials.dialphone.encyption.exceptions.MalformedEncryptedEvent
import de.mtorials.dialphone.encyption.exceptions.UnexpectedEncryptionAlgorithmException
import de.mtorials.dialphone.api.ids.roomId

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
        encryptionManager.handleKeyEvent(e, senderKey)
    }
}