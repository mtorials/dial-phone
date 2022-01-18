package de.mtorials.dialphone.api.model.mevents.roomstate

import de.mtorials.dialphone.api.model.MatrixRoomEvent
import de.mtorials.dialphone.api.model.mevents.EventContent

interface MatrixStateEvent : MatrixRoomEvent {
    val stateKey: String
    val prevContent: EventContent?
    val id: String?
    override val content: StateEventContent
}