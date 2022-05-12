package de.mtorials.dialphone.api.listeners

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent

interface GenericListener<T : DialPhoneApi> {
    suspend fun onJoinedRoomStateEvent(event: MatrixStateEvent, roomId: RoomId, phone: T, isOld: Boolean) = Unit
    suspend fun onJoinedRoomMessageEvent(event: MatrixEvent, roomId: RoomId, phone: T, isOld: Boolean) = Unit
    suspend fun onInvitedRoomStateEvent(event: MatrixStateEvent, roomId: RoomId, phone: T, isOld: Boolean) = Unit
    suspend fun onToDeviceEvent(event: MatrixEvent, phone: T, isOld: Boolean) = Unit
    suspend fun onPresenceEvent(event: MatrixEvent, phone: T, isOld: Boolean) = Unit
}