package de.mtorials.dialphone.core.entityfutures

import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.core.entities.room.JoinedRoom

/**
 * Represents a room entity without loaded properties
 */
interface RoomFuture : EntityFuture<JoinedRoom>, RoomActions {
    /**
     * @return all matrix state events corresponding to this room
     */
    suspend fun receiveStateEvents() : List<MatrixStateEvent>
}