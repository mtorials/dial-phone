package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.model.enums.Membership
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.core.DialPhoneImpl

class StateCacheListener (
    val stateCache: StateCache
) : GenericListener<DialPhoneImpl> {

    override suspend fun onJoinedRoomStateEvent(event: MatrixStateEvent, roomId: RoomId, phone: DialPhoneImpl, isOld: Boolean) {
        handleStateEvent(event, roomId)
    }

    override suspend fun onJoinedRoomTimelineEvent(
        event: MatrixEvent,
        roomId: RoomId,
        phone: DialPhoneImpl,
        isOld: Boolean
    ) {
        if (event is MatrixStateEvent) handleStateEvent(event, roomId)
    }

    private suspend fun handleStateEvent(stateEvent: MatrixStateEvent, roomId: RoomId) {
        stateCache.insertRoomStateEvent(roomId = roomId, stateEvent)
        stateCache.insertRoomId(membership = Membership.JOIN, roomId = roomId)
    }

    override suspend fun onInvitedRoomStateEvent(
        event: MatrixStateEvent,
        roomId: RoomId,
        phone: DialPhoneImpl,
        isOld: Boolean
    ) {
        stateCache.insertRoomStateEvent(roomId = roomId, event)
        stateCache.insertRoomId(membership = Membership.INVITE, roomId = roomId)
    }
}