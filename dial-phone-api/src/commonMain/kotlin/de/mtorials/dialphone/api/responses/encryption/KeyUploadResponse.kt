package de.mtorials.dialphone.api.responses.encryption

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
/**
 * https://spec.matrix.org/v1.1/client-server-api/#key-management-api
 */
data class KeyUploadResponse(
    /**
     * For each key algorithm,
     * the number of unclaimed one-time keys of that type currently held on the server for this device.
     */
    @SerialName("one_time_key_counts")
    val oneTimeKeyCount: Map<String, Int> = emptyMap(),
)