package de.mtorials.dialphone.api.listeners

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.model.mevents.MatrixEvent

interface Listener {
    fun onNewRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneApi)
    fun onOldRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneApi)
}