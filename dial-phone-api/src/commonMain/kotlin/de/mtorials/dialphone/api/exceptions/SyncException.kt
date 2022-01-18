package de.mtorials.dialphone.api.exceptions

class SyncException(
    override val cause: Throwable? = null,
    override val message: String
) : RuntimeException()