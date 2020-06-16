package de.mtorials.dialphone.entities.actions

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.mevents.EventContent
import de.mtorials.dialphone.mevents.MatrixEvent
import kotlin.reflect.KClass

interface RoomActions {
    val phone: DialPhone
    /**
     * Sends an MatrixEvent and returns the Id
     * @param event A MatrixEvent event
     * @return The event Id
     */
    suspend fun sendEvent(eventType: KClass<out MatrixEvent>, content: EventContent) : String
}