package de.mtorials.dialphone.api.model.mevents.roomstate

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.model.mevents.MatrixRoomEvent

interface MatrixStateEvent : MatrixRoomEvent {
    val stateKey: String
    val prevContent: StateEventContent?
    val id: EventId?
    override val content: StateEventContent
}