package de.mtorials.dialphone.core.encryption

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EncryptionListener(
    private val encryptionManager: EncryptionManager,
) : GenericListener<DialPhoneApi> {
    override fun onRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneApi, isOld: Boolean) = Unit

    override fun onToDeviceEvent(event: MatrixEvent, phone: DialPhoneApi, isOld: Boolean) {
        println(Json { prettyPrint = true }.encodeToString(event))
    }

    override fun onPresenceEvent(event: MatrixEvent, phone: DialPhoneApi, isOld: Boolean) = Unit
}