package de.mtorials.dialphone.api.listeners

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlin.reflect.KClass


/**
 *
 * Implement this class to listen specific custom events
 *
 * @param T The type of event you want to listen for
 * @param type The class of the event type you want to listen for
 *
 */
abstract class CustomApiEventAdapter<T : MatrixEvent>(
    private val type : KClass<T>,
    private val receivePastEvent: Boolean = false
) : Listener {

    /**
     * @param event The event you listened for
     * @param roomId The id of the room the event occurred in
     */
    abstract fun onMatrixEvent(event: T, roomId: String)

    override fun onRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneApi, isOld: Boolean) {
        if (type.isInstance(event)) onMatrixEvent(
            event as T,
            roomId
        )
    }

}