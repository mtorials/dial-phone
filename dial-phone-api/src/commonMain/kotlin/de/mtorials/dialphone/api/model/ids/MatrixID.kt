package de.mtorials.dialphone.api.model.ids

abstract class MatrixID {

    abstract val type: IDType
    abstract val value: String

    override fun toString(): String = value

    enum class IDType(val symbol: String) {
        USER("@"), ROOM("!"), ROOM_ALIAS("#"), COMMUNITY("+"), EVENT("$")
    }

    companion object {
        private const val SEPARATOR = ":"
    }
}