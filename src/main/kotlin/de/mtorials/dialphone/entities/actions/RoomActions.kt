package de.mtorials.dialphone.entities.actions

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.mevents.EventContent
import de.mtorials.dialphone.mevents.roommessage.MessageEventContent
import de.mtorials.dialphone.mevents.roomstate.StateEventContent

interface RoomActions {
    val phone: DialPhone
    /**
     * Sends an MatrixEvent and returns the Id
     * @param content An EventContent annotated with the event
     * @return The event Id
     */
    suspend fun sendMessageEvent(content: MessageEventContent) : String
    suspend fun sendStateEvent(content: StateEventContent, stateKey: String) : String
}