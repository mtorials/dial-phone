package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent

interface RoomCache {

    /**
     * @return the cached state events for a given room
     */
    fun getRoomStateEvents(roomId: RoomId) : List<MatrixStateEvent>

    /**
     * You have to check for duplicates
     * uniquely identified by type and statekey
     */
    fun insertRoomStateEvent(roomId: RoomId, event: MatrixStateEvent)

    var joinedRoomIds: Collection<RoomId>
    var invitedRoomIds: Collection<RoomId>
    var knockedRoomIds: Collection<RoomId>
    var leftRoomIds: Collection<RoomId>
}