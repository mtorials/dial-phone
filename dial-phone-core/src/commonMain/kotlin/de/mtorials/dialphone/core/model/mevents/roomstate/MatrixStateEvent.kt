package de.mtorials.dialphone.core.model.mevents.roomstate

import de.mtorials.dialphone.core.model.MatrixRoomEvent
import de.mtorials.dialphone.core.model.mevents.EventContent

interface MatrixStateEvent : MatrixRoomEvent {
    val stateKey: String
    val prevContent: EventContent?
    val id: String?
    override val content: StateEventContent
}