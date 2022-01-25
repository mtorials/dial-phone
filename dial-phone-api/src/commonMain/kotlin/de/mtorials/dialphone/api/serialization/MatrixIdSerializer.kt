package de.mtorials.dialphone.api.serialization

import de.mtorials.dialphone.api.ids.MatrixID
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Encoder

abstract class MatrixIdSerializer<T : MatrixID>(
    private val serialName: String,
) : KSerializer<T> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(serialName, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: T) {
        encoder.encodeString(value.value)
    }

    protected fun checkIfType(value: String, type: MatrixID.IDType) {
        if (!value.startsWith(type.symbol)) throw SerializationException("ID type does not match the given id.")
    }
}