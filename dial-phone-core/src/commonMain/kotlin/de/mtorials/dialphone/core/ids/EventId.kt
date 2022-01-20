package de.mtorials.dialphone.core.ids


class EventId(
    override val value: String,
) : MatrixID() {
    override val type = IDType.EVENT
}