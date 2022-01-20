package de.mtorials.dialphone.api.model.ids

class RoomId(
    override val value: String,
) : MatrixID() {
    override val type = MatrixID.IDType.ROOM
}