package de.mtorials.dialphone.entities.actions

import de.mtorials.dialphone.core.model.mevents.roommessage.MessageEventContent
import de.mtorials.dialphone.core.model.mevents.roomstate.StateEventContent
import de.mtorials.dialphone.entities.DialPhone

/**
 * All actions you can perform on a room
 */
interface RoomActions {
    val phone: DialPhone
    val id: String

    /**
     * Sends an MatrixMessageEvent and returns the id
     * @param content An EventContent annotated with the event
     * @param eventType The matrix event type (e.g. m.room.message)
     * @return The event Id
     */
    suspend fun sendMessageEvent(content: MessageEventContent, eventType: String) : String

    /**
     * Sends an MatrixStateEvent and returns the id
     * @param content An EventContent annotated with the event
     * @param eventType The matrix event type (e.g. m.room.message)
     * @param stateKey The state key. Mostly empty. See matrix specifications
     * @return The event Id
     */
    suspend fun sendStateEvent(content: StateEventContent, eventType: String, stateKey: String = "") : String

    /**
     * Leave the room
     */
    suspend fun leave()
}