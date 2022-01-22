package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.model.mevents.MatrixEvent

interface RoomEventHook {
    fun manipulateEvent(event: MatrixEvent) : MatrixEvent
}