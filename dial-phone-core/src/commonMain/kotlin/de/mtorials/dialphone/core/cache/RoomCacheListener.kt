package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.core.DialPhoneImpl

class RoomCacheListener (
    val roomCache: RoomCache
) : GenericListener<DialPhoneImpl> {

    override fun onJoinedRoomStateEvent(event: MatrixStateEvent, roomId: RoomId, phone: DialPhoneImpl, isOld: Boolean) {
        roomCache.insertRoomStateEvent(roomId = roomId, event)
        if (roomId !in roomCache.joinedRoomIds) roomCache.joinedRoomIds.add(roomId)
    }
}