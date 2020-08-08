package de.mtorials.dialphone.entities.actions

import de.mtorials.dialphone.DialPhone
import net.micromes.makocommons.mevents.roommessage.MessageEventContent
import net.micromes.makocommons.mevents.roomstate.StateEventContent

/**
 * All actions you can perform on a room
 */
interface RoomActions {
    val phone: DialPhone
    val id: String

    /**
     * Sends an MatrixMessageEvent and returns the id
     * @param content An EventContent annotated with the event
     * @return The event Id
     */
    suspend fun sendMessageEvent(content: MessageEventContent) : String

    /**
     * Sends an MatrixStateEvent and returns the id
     * @param content An EventContent annotated with the event
     * @param stateKey The state key. Mostly empty. See matrix specifications
     * @return The event Id
     */
    suspend fun sendStateEvent(content: StateEventContent, stateKey: String = "") : String
}