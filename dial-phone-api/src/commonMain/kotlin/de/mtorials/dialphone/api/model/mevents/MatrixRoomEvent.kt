package de.mtorials.dialphone.api.model.mevents

import de.mtorials.dialphone.api.model.mevents.MatrixEvent

/**
 * A matrix event that relates to a room
 */
interface MatrixRoomEvent : MatrixEvent {
     val originServerTs: Long?
}