package de.mtorials.dialphone.api.listeners

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.model.mevents.MatrixEvent

interface Listener {
    fun onRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneApi, isOld: Boolean)
}