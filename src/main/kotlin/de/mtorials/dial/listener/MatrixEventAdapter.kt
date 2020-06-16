package de.mtorials.dial.listener

import de.mtorials.dial.DialPhone
import de.mtorials.dial.entityfutures.RoomFuture
import de.mtorials.dial.mevents.MatrixEvent
import kotlin.reflect.KClass


/**
 *
 * Implement this class to listen specific custom events
 *
 * @param T The type of event you want to listen for
 * @param type The class of the event type you want to listen for
 *
 */
abstract class MatrixEventAdapter<T : MatrixEvent>(private val type : KClass<T>) : Listener {
    override fun onRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone) {
        if (type.isInstance(event)) onMatrixEvent(event as T, RoomFuture(roomId, phone))
    }

    /**
     * @param event The event you listened for
     * @param roomFuture The RoomFuture of the room the event occurred in
     */
    abstract fun onMatrixEvent(event: T, roomFuture: RoomFuture)
}