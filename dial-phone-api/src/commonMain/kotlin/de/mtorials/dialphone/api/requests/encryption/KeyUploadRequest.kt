package de.mtorials.dialphone.api.requests.encryption

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * https://spec.matrix.org/v1.1/client-server-api/#key-management-api
 */
@Serializable
data class KeyUploadRequest(
    @SerialName("device_keys")
    val deviceKeys: SignedDeviceKeys? = null,
    @SerialName("one_time_keys")
    /**
     * One-time public keys for “pre-key” messages.
     * The names of the properties should be in the format <algorithm>:<key_id>.
     * The format of the key is determined by the key algorithm.
     */
    val oneTimeKeys: Map<String, JsonElement>? = null,
)