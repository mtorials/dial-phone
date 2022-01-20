package de.mtorials.dialphone.core.ids

class RoomAlias(
    override val value: String,
) : MatrixID() {
    override val type = IDType.ROOM_ALIAS
}