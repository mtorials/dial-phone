package de.mtorials.dialphone.api.listeners

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.model.mevents.MatrixEvent

interface GenericListener<T : DialPhoneApi> {
    fun onRoomEvent(event: MatrixEvent, roomId: String, phone: T, isOld: Boolean)
    fun onToDeviceEvent(event: MatrixEvent, phone: T, isOld: Boolean)
    fun onPresenceEvent(event: MatrixEvent, phone: T, isOld: Boolean)
}