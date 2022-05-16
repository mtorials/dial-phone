package de.mtorials.dialphone.api.logging

enum class DialPhoneLogLevel(
    val level: Int,
) {
    NONE(0),
    ERROR(5),
    SYNC_EXCEPTIONS(10),
    DEBUG(15),
    TRACE(20),
}