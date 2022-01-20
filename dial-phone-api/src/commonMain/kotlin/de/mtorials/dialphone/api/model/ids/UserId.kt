package de.mtorials.dialphone.api.model.ids

class UserId(
    override val value: String,
) : MatrixID() {
    override val type = IDType.USER
}