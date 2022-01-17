package de.mtorials.dialphone.core.listener

import de.mtorials.dialphone.core.DialPhoneCore
import de.mtorials.dialphone.core.model.mevents.MatrixEvent

interface Listener {
    fun onNewRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneCore)
    fun onOldRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneCore)
}