package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.core.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.entities.entities.Room
import de.mtorials.dialphone.entities.actions.RoomActions

/**
 * Represents a room entity without loaded properties
 */
interface RoomFuture : EntityFuture<Room>, RoomActions {
    /**
     * @return all matrix state events corresponding to this room
     */
    suspend fun receiveStateEvents() : List<MatrixStateEvent>
}