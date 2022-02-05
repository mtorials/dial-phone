package de.mtorials.dialphone.encyption.exceptions

import de.mtorials.dialphone.api.exceptions.SyncException

open class EncryptionException(
    message: String,
    cause: Throwable? = null,
) : SyncException(
    cause = cause,
    message = message,
)