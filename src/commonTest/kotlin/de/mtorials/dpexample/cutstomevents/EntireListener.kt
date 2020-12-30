package de.mtorials.dpexample.cutstomevents

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.listener.Listener
import net.mt32.makocommons.mevents.MatrixEvent

class EntireListener : Listener {
    override fun onNewRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone) {
        println("Received state event ${event::class.simpleName}. Content is ${event.content}.")
    }

    override fun onOldRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone) {
        return
    }
}