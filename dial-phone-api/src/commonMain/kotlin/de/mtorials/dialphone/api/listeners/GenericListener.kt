package de.mtorials.dialphone.api.listeners

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent

interface GenericListener<T : DialPhoneApi> {
    fun onJoinedRoomStateEvent(event: MatrixStateEvent, roomId: RoomId, phone: T, isOld: Boolean) = Unit
    fun onJoinedRoomMessageEvent(event: MatrixEvent, roomId: RoomId, phone: T, isOld: Boolean) = Unit
    fun onInvitedRoomStateEvent(event: MatrixStateEvent, roomId: RoomId, phone: T, isOld: Boolean) = Unit
    fun onToDeviceEvent(event: MatrixEvent, phone: T, isOld: Boolean) = Unit
    fun onPresenceEvent(event: MatrixEvent, phone: T, isOld: Boolean) = Unit
}