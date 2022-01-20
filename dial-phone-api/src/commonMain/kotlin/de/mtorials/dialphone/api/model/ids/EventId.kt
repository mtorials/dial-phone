package de.mtorials.dialphone.api.model.ids

class EventId(
    override val value: String,
) : MatrixID() {
    override val type = IDType.EVENT
}