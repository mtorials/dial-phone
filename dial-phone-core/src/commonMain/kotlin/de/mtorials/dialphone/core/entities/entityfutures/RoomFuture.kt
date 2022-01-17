package de.mtorials.dialphone.core.entities.entityfutures

import de.mtorials.dialphone.core.model.mevents.roomstate.MatrixStateEvent

/**
 * Represents a room entity without loaded properties
 */
interface RoomFuture : de.mtorials.dialphone.core.entities.entityfutures.EntityFuture<de.mtorials.dialphone.core.entities.Room>,
    de.mtorials.dialphone.core.entities.actions.RoomActions {
    /**
     * @return all matrix state events corresponding to this room
     */
    suspend fun receiveStateEvents() : List<MatrixStateEvent>
}