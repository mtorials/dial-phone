package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.entities.Room
import de.mtorials.dialphone.entities.actions.RoomActions
import net.mt32.makocommons.mevents.roomstate.MatrixStateEvent

/**
 * Represents a room entity without loaded properties
 */
interface RoomFuture : EntityFuture<Room>, RoomActions {
    /**
     * @return all matrix state events corresponding to this room
     */
    suspend fun receiveStateEvents() : Array<MatrixStateEvent>
}