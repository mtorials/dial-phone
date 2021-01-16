package de.mtorials.dialphone.model

/**
 * Represents a matrix event of type room message event
 */
interface MatrixMessageEvent : MatrixRoomEvent {
    val id: String
    override val content: MessageEventContent
}