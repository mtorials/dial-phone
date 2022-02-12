package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent

class InMemoryCache : PhoneCache {
    override val roomCache = object : RoomCache {

        private val states: MutableMap<RoomId, MutableList<MatrixStateEvent>> = mutableMapOf()

        override fun getRoomStateEvents(roomId: RoomId): List<MatrixStateEvent> = states[roomId] ?: emptyList()

        // TODO testing!
        override fun insertRoomStateEvent(roomId: RoomId, event: MatrixStateEvent) {
            states[roomId] = states[roomId]?.filter {
                !(event::class.isInstance(it) && event.stateKey == it.stateKey)
            }?.toMutableList() ?: mutableListOf()
            states[roomId]?.add(event)
        }

        override var joinedRoomIds: Collection<RoomId> = listOf()
        override var invitedRoomIds: Collection<RoomId> = listOf()
        override var knockedRoomIds: Collection<RoomId> = listOf()
        override var leftRoomIds: Collection<RoomId> = listOf()
    }
}