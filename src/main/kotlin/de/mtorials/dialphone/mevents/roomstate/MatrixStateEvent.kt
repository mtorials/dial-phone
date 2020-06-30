package de.mtorials.dialphone.mevents.roomstate

import com.fasterxml.jackson.annotation.JsonTypeInfo
import de.mtorials.dialphone.mevents.EventContent
import de.mtorials.dialphone.mevents.DefaultEvent
import de.mtorials.dialphone.mevents.MatrixRoomEvent

interface MatrixStateEvent : MatrixRoomEvent {
    val stateKey: String
    val prevContent: EventContent?
    val id: String?
    override val content: StateEventContent
}