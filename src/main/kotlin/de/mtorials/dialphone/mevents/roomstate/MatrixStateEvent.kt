package de.mtorials.dialphone.mevents.roomstate

import de.mtorials.dialphone.mevents.EventContent
import de.mtorials.dialphone.mevents.MatrixRoomEvent

interface MatrixStateEvent : MatrixRoomEvent {
    val stateKey: String
    val prevContent: EventContent?
    override val content: StateEventContent
}