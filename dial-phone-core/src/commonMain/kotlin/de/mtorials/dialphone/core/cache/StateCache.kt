package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.enums.Membership
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent

interface StateCache {

    /**
     * @return the cached state events for a given room
     */
    fun getRoomStateEvents(roomId: RoomId) : List<MatrixStateEvent>

    /**
     * You have to check for duplicates
     * uniquely identified by type and statekey
     */
    fun insertRoomStateEvent(roomId: RoomId, event: MatrixStateEvent)

    val roomIds: Map<Membership, Collection<RoomId>>
    suspend fun insertRoomId(membership: Membership, roomId: RoomId)
}