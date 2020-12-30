package net.mt32.makocommons.mevents.roommessage

import net.mt32.makocommons.mevents.MatrixRoomEvent

/**
 * Represents a matrix event of type room message event
 */
interface MatrixMessageEvent : MatrixRoomEvent {
    val id: String
    override val content: MessageEventContent
}