package net.mt32.makocommons.mevents.roomstate

import net.mt32.makocommons.mevents.EventContent
import net.mt32.makocommons.mevents.MatrixRoomEvent

interface MatrixStateEvent : MatrixRoomEvent {
    val stateKey: String
    val prevContent: EventContent?
    val id: String?
    override val content: StateEventContent
}