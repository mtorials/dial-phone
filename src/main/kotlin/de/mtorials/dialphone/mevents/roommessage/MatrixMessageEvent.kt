package de.mtorials.dialphone.mevents.roommessage

import de.mtorials.dialphone.mevents.MatrixRoomEvent

/**
 * Represents a matrix event of type room message event
 */
interface MatrixMessageEvent : MatrixRoomEvent {
    override val content: MessageEventContent
}