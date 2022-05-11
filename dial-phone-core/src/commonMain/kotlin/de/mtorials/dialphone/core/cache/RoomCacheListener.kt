package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.core.DialPhoneImpl

class RoomCacheListener (
    val roomCache: RoomCache
) : GenericListener<DialPhoneImpl> {

    override fun onRoomEvent(event: MatrixEvent, roomId: RoomId, phone: DialPhoneImpl, isOld: Boolean) {
        if (event !is MatrixStateEvent) return
        roomCache.insertRoomStateEvent(roomId = roomId, event)
    }
}