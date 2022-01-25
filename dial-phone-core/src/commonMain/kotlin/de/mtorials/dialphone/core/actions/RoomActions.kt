package de.mtorials.dialphone.core.actions

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.model.mevents.roommessage.MessageEventContent
import de.mtorials.dialphone.api.model.mevents.roomstate.StateEventContent
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.api.ids.RoomId

/**
 * All actions you can perform on a room
 */
interface RoomActions {
    val phone: DialPhone
    val id: RoomId

    /**
     * Sends an MatrixMessageEvent and returns the id
     * @param content An EventContent annotated with the event
     * @param eventType The matrix event type (e.g. m.room.message)
     * @return The event Id
     */
    suspend fun sendMessageEvent(content: MessageEventContent, eventType: String) : EventId

    /**
     * Sends an MatrixStateEvent and returns the id
     * @param content An EventContent annotated with the event
     * @param eventType The matrix event type (e.g. m.room.message)
     * @param stateKey The state key. Mostly empty. See matrix specifications
     * @return The event Id
     */
    suspend fun sendStateEvent(content: StateEventContent, eventType: String, stateKey: String = "") : EventId

    /**
     * Leave the room
     */
    suspend fun leave()
}