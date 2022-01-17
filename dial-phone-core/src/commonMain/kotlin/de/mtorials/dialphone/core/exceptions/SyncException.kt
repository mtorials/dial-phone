package de.mtorials.dialphone.core.exceptions

class SyncException(
    override val cause: Throwable? = null,
    override val message: String
) : RuntimeException()