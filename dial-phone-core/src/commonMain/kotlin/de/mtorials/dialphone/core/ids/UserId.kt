package de.mtorials.dialphone.core.ids

class UserId(
    override val value: String,
) : MatrixID() {
    override val type = IDType.USER
}