package de.mtorials.dialphone.api.responses

import kotlinx.serialization.Serializable

/**
 * NOT TO SPEC!
 * All fields are required, but synapse answers with empty json if turn is not configured.
 * See [this issue](https://github.com/matrix-org/matrix-doc/issues/1663)
 */
@Serializable
data class TurnCredentialsResponse(
    val password: String? = null,
    val ttl: Int? = null,
    val uris: List<String>? = null,
    val username: String? = null,
)