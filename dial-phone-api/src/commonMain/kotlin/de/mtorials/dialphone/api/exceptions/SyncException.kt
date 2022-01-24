package de.mtorials.dialphone.api.exceptions

open class SyncException(
    override val cause: Throwable? = null,
    override val message: String
) : RuntimeException()