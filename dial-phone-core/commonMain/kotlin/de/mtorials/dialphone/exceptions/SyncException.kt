package de.mtorials.dialphone.exceptions

class SyncException(
    override val cause: Throwable? = null,
    override val message: String
) : RuntimeException()