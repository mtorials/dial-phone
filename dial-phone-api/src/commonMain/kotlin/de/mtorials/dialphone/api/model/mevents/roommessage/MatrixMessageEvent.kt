package de.mtorials.dialphone.api.model.mevents.roommessage

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.model.mevents.MatrixRoomEvent

/**
 * Represents a matrix event of type room message event
 */
interface MatrixMessageEvent : MatrixRoomEvent {
    val id: EventId
    override val content: MessageEventContent
}