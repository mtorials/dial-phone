package de.mtorials.dialphone.api.ids

import de.mtorials.dialphone.api.serialization.MatrixIdSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder


@Serializable(with = EventIdSerializer::class)
class EventId(
    override val value: String,
) : MatrixID() {
    override val type = IDType.EVENT
}

class EventIdSerializer : MatrixIdSerializer<EventId>("eventId") {
    override fun deserialize(decoder: Decoder): EventId {
        val value = decoder.decodeString()
        checkIfType(value, MatrixID.IDType.EVENT)
        return EventId(value)
    }
}