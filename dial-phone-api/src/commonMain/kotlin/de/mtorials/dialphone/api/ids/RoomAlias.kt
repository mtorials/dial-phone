package de.mtorials.dialphone.api.ids

import de.mtorials.dialphone.api.serialization.MatrixIdSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder

@Serializable(with = RoomAliasSerializer::class)
class RoomAlias(
    override val value: String,
) : MatrixID() {
    override val type = IDType.ROOM_ALIAS
}

class RoomAliasSerializer : MatrixIdSerializer<RoomAlias>("roomAlias") {
    override fun deserialize(decoder: Decoder): RoomAlias {
        val value = decoder.decodeString()
        checkIfType(value, MatrixID.IDType.ROOM_ALIAS)
        return RoomAlias(value)
    }
}