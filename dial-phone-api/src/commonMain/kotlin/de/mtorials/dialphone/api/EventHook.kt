package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.model.mevents.MatrixEvent

interface EventHook {
    fun manipulateIncomingRoomEvent(event: MatrixEvent) : MatrixEvent
    fun manipulateOutgoingMessageEvent(type: String, content: EventContent) : Pair<String, EventContent>
}