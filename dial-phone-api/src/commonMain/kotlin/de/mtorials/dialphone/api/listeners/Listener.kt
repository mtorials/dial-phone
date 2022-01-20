package de.mtorials.dialphone.api.listeners

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.model.ids.RoomId
import de.mtorials.dialphone.api.model.mevents.MatrixEvent

interface Listener {
    fun onRoomEvent(event: MatrixEvent, roomId: RoomId, phone: DialPhoneApi, isOld: Boolean)
}