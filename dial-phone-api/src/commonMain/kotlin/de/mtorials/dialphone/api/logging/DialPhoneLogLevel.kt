package de.mtorials.dialphone.api.logging

data class DialPhoneLogLevel(
    val sync: LoggingPolicy,
    val crypto: LoggingPolicy,
) {
    data class LoggingPolicy(val message: Boolean, val traces: Boolean)

    companion object {
        val ALL_MESSAGE = DialPhoneLogLevel(
            sync = LoggingPolicy(true, false),
            crypto = LoggingPolicy(true, false),
        )
        val NONE = DialPhoneLogLevel(
            sync = LoggingPolicy(false, false),
            crypto = LoggingPolicy(false, false),
        )
    }
}