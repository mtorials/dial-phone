package de.mtorials.dialphone.model.mevents.roommessage

import de.mtorials.dialphone.model.MatrixRoomEvent

/**
 * Represents a matrix event of type room message event
 */
interface MatrixMessageEvent : MatrixRoomEvent {
    val id: String
    override val content: MessageEventContent
}