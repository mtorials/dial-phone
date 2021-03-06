package de.mtorials.dialphone.listener

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.model.mevents.MatrixEvent

interface Listener {
    fun onNewRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone)
    fun onOldRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone)
}