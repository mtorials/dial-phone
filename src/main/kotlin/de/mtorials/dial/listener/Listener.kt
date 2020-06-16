package de.mtorials.dial.listener

import de.mtorials.dial.DialPhone
import de.mtorials.dial.mevents.MatrixEvent

interface Listener {
    fun onRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone)
}