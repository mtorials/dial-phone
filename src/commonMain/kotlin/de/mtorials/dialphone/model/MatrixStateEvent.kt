package de.mtorials.dialphone.model

interface MatrixStateEvent : MatrixRoomEvent {
    val stateKey: String
    val prevContent: EventContent?
    val id: String?
    override val content: StateEventContent
}