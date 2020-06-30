package de.mtorials.dialphone.mevents.roommessage

import de.mtorials.dialphone.mevents.MatrixRoomEvent

/**
 * Represents a matrix event of type room message event
 */
interface MatrixMessageEvent : MatrixRoomEvent {
    val id: String
    override val content: MessageEventContent
}