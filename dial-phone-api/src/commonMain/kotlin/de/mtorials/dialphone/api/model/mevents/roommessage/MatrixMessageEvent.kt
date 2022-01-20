package de.mtorials.dialphone.api.model.mevents.roommessage

import de.mtorials.dialphone.api.model.MatrixRoomEvent

/**
 * Represents a matrix event of type room message event
 */
interface MatrixMessageEvent : MatrixRoomEvent {
    val id: String
    override val content: MessageEventContent
}