package de.mtorials.dialphone.model.mevents.roomstate

import de.mtorials.dialphone.model.MatrixRoomEvent
import de.mtorials.dialphone.model.mevents.EventContent

interface MatrixStateEvent : MatrixRoomEvent {
    val stateKey: String
    val prevContent: EventContent?
    val id: String?
    override val content: StateEventContent
}