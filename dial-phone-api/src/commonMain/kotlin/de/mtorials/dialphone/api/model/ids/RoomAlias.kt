package de.mtorials.dialphone.api.model.ids

class RoomAlias(
    override val value: String,
) : MatrixID() {
    override val type = IDType.ROOM_ALIAS
}