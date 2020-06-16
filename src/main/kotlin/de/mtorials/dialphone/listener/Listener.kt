package de.mtorials.dialphone.listener

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.mevents.MatrixEvent

interface Listener {
    fun onRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone)
}