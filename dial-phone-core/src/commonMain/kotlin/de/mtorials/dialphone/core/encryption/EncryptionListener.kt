package de.mtorials.dialphone.core.encryption

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.model.enums.MessageEncryptionAlgorithm
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomEncrypted
import de.mtorials.dialphone.api.model.mevents.todevice.MRoomKey
import de.mtorials.dialphone.core.exceptions.MalformedEncryptedEvent
import de.mtorials.dialphone.core.exceptions.UnexpectedEncryptionAlgorithmException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EncryptionListener(
    private val encryptionManager: EncryptionManager,
) : GenericListener<DialPhoneApi> {
    override fun onRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneApi, isOld: Boolean) = Unit

    override fun onToDeviceEvent(event: MatrixEvent, phone: DialPhoneApi, isOld: Boolean) {
        // TODO remove
        println(Json { prettyPrint = true }.encodeToString(event))
        if (event !is MRoomEncrypted) return
        if (event.content.algorithm != MessageEncryptionAlgorithm.OLM_V1_CURVE25519_AES_SHA1)
            throw UnexpectedEncryptionAlgorithmException("to-device")
        val senderKey = event.content.senderKey ?: throw MalformedEncryptedEvent(event)
        val e = encryptionManager.decryptOlm(event)
        if (e !is MRoomKey) return
        encryptionManager.handleKeyEvent(e, senderKey)
    }

    override fun onPresenceEvent(event: MatrixEvent, phone: DialPhoneApi, isOld: Boolean) = Unit
}