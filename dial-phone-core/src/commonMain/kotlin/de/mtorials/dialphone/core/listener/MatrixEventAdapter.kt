package de.mtorials.dialphone.core.listener

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.model.mevents.MatrixEvent
import kotlin.reflect.KClass

/**
 *
 * Implement this class to listen specific custom events
 *
 * @param T The type of event you want to listen for
 * @param type The class of the event type you want to listen for
 *
 */
abstract class MatrixEventAdapter<T : MatrixEvent>(
    private val type : KClass<T>,
    private val receivePastEvent: Boolean = false
) : Listener {
    @Suppress
    override fun onNewRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone) {
        if (type.isInstance(event)) onMatrixEvent(event as T,
            de.mtorials.dialphone.core.entities.entityfutures.RoomFutureImpl(roomId, phone)
        )
    }
    @Suppress
    override fun onOldRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone) {
        if (!receivePastEvent) return
        if (type.isInstance(event)) onMatrixEvent(event as T,
            de.mtorials.dialphone.core.entities.entityfutures.RoomFutureImpl(roomId, phone)
        )
    }

    /**
     * @param event The event you listened for
     * @param roomFuture The RoomFuture of the room the event occurred in
     */
    abstract fun onMatrixEvent(event: T, roomFuture: de.mtorials.dialphone.core.entities.entityfutures.RoomFutureImpl)
}