package de.mtorials.dialphone.core.exceptions.encryption

import de.mtorials.dialphone.api.exceptions.SyncException

open class EncryptionException(
    message: String,
    cause: Throwable? = null,
) : SyncException(
    cause = cause,
    message = message,
)