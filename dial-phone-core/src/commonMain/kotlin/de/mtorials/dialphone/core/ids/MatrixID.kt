package de.mtorials.dialphone.core.ids

abstract class MatrixID {

    abstract val type: IDType
    abstract val value: String

    override fun toString(): String = value
    override fun hashCode(): Int {
        return value.hashCode()
    }
    override fun equals(other: Any?): Boolean {
        return this.value == (other as MatrixID).value
    }

    enum class IDType(val symbol: String) {
        USER("@"), ROOM("!"), ROOM_ALIAS("#"), COMMUNITY("+"), EVENT("$")
    }

    companion object {
        private const val SEPARATOR = ":"
    }
}