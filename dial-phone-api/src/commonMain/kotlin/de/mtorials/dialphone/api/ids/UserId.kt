package de.mtorials.dialphone.api.ids

import de.mtorials.dialphone.api.serialization.MatrixIdSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder

@Serializable(with = UserIdSerializer::class)
class UserId(
    override val value: String,
) : MatrixID() {
    override val type = IDType.USER
}

class UserIdSerializer : MatrixIdSerializer<UserId>("userId") {
    override fun deserialize(decoder: Decoder): UserId {
        val value = decoder.decodeString()
        checkIfType(value, MatrixID.IDType.USER)
        return UserId(value)
    }
}