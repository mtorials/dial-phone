package de.mtorials.dialphone.mevents

interface MatrixStateEvent : MatrixRoomEvent {
    val stateKey: String
    val prevContent: EventContent?
}