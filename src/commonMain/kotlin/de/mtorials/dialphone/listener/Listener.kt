package de.mtorials.dialphone.listener

import de.mtorials.dialphone.DialPhone
import net.mt32.makocommons.mevents.MatrixEvent

interface Listener {
    fun onNewRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone)
    fun onOldRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone)
}