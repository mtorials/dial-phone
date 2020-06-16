package de.mtorials.dialphone.entities.actions

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.mevents.EventContent

interface RoomActions {
    val phone: DialPhone
    /**
     * Sends an MatrixEvent and returns the Id
     * @param content An EventContent annotated with the event
     * @return The event Id
     */
    suspend fun sendEvent(content: EventContent) : String
}