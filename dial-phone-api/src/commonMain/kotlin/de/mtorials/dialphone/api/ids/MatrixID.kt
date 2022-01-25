package de.mtorials.dialphone.api.ids

import kotlin.reflect.KClass

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
        USER("@"), ROOM("!"), ROOM_ALIAS("#"), EVENT("$")
    }

    companion object {
        fun fromString(value: String) : MatrixID {
            return when(IDType.valueOf(value[0].toString())) {
                IDType.USER -> UserId(value)
                IDType.ROOM -> RoomId(value)
                IDType.ROOM_ALIAS -> RoomAlias(value)
                IDType.EVENT -> EventId(value)
            }
        }
    }
}