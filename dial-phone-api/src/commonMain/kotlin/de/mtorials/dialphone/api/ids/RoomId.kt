package de.mtorials.dialphone.api.ids

import de.mtorials.dialphone.api.serialization.MatrixIdSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder

@Serializable(with = RoomIdSerializer::class)
class RoomId(
    override val value: String,
) : MatrixID() {
    override val type = IDType.ROOM
}

class RoomIdSerializer : MatrixIdSerializer<RoomId>("roomId") {
    override fun deserialize(decoder: Decoder): RoomId {
        val value = decoder.decodeString()
        checkIfType(value, MatrixID.IDType.ROOM)
        return RoomId(value)
    }
}