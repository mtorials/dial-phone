package de.mtorials.dialphone.core.ids

class RoomId(
    override val value: String,
) : MatrixID() {
    override val type = IDType.ROOM
}