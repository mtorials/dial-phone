package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.entities.Room
import de.mtorials.dialphone.entities.actions.RoomActions
import de.mtorials.dialphone.model.mevents.roomstate.MatrixStateEvent

/**
 * Represents a room entity without loaded properties
 */
interface RoomFuture : EntityFuture<Room>, RoomActions {
    /**
     * @return all matrix state events corresponding to this room
     */
    suspend fun receiveStateEvents() : List<MatrixStateEvent>
}