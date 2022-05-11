package de.mtorials.dialphone.api.listeners

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.mevents.MatrixEvent

interface GenericListener<T : DialPhoneApi> {
    fun onRoomEvent(event: MatrixEvent, roomId: RoomId, phone: T, isOld: Boolean) = Unit
    fun onToDeviceEvent(event: MatrixEvent, phone: T, isOld: Boolean) = Unit
    fun onPresenceEvent(event: MatrixEvent, phone: T, isOld: Boolean) = Unit
}