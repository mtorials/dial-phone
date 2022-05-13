package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.enums.Membership
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import kotlin.reflect.KType

interface StateCache {

    /**
     * @return the cached state events for a given room
     */
    fun getRoomStateEvents(roomId: RoomId) : List<MatrixStateEvent>

    /**
     * @return the cached state events for a given room and matrix event type
     */
    fun <T : MatrixStateEvent> getRoomStateEvents(roomId: RoomId, type: String) : List<T>

    /**
     * You have to check for duplicates
     * uniquely identified by type and statekey
     */
    fun insertRoomStateEvent(roomId: RoomId, event: MatrixStateEvent)

    val roomIds: Map<Membership, Collection<RoomId>>
    suspend fun insertRoomId(membership: Membership, roomId: RoomId)
}