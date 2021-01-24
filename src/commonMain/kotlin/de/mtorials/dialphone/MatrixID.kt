package de.mtorials.dialphone

class MatrixID(
        val homeServerName: String,
        val type: IDType,
        val value: String
) {
    enum class IDType(val symbol: String) {
        USER("@"), ROOM("!"), ROOM_ALIAS("#"), COMMUNITY("+"), EVENT("$")
    }

    override fun toString(): String = type.symbol + value + SEPARATOR + homeServerName
    override fun equals(other: Any?): Boolean {
        if (other !is MatrixID) return false
        return homeServerName == other.homeServerName && other.type.symbol == type.symbol && other.value == value
    }
    override fun hashCode(): Int {
        var result = homeServerName.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }

    companion object {
        private const val SEPARATOR : String = ":"

        fun fromString(serialized: String) : MatrixID {
            val split = serialized.split(SEPARATOR)
            return MatrixID(
                homeServerName = split[1],
                type = IDType.values().filter { it.symbol == split[0].first().toString() }[0],
                value = split[0].removeRange(0..0)
            )
        }
    }
}