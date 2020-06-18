package de.mtorials.dialphone.mevents.roommessage

import de.mtorials.dialphone.mevents.MatrixRoomEvent

interface MatrixMessageEvent : MatrixRoomEvent {
    override val content: MessageEventContent
}