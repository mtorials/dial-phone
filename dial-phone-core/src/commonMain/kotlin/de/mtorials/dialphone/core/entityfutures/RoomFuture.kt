package de.mtorials.dialphone.core.entityfutures

import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.core.entities.Room
import de.mtorials.dialphone.core.actions.RoomActions

/**
 * Represents a room entity without loaded properties
 */
interface RoomFuture : EntityFuture<Room>, RoomActions {
    /**
     * @return all matrix state events corresponding to this room
     */
    suspend fun receiveStateEvents() : List<MatrixStateEvent>
}