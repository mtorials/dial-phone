package de.mtorials.dialphone.api.requests.encryption

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * https://spec.matrix.org/v1.1/client-server-api/#key-management-api
 */
@Serializable
data class DeviceKeys(
    val algorithms: List<String>,
    @SerialName("device_id")
    val deviceId: String,
    val keys: Map<String, String>,
    /**
     * Signatures for the device key object. A map from user ID, to a map from <algorithm>:<device_id> to the signature.
     * The signature is calculated using the process described at Signing JSON.
     */
    val signatures: Map<String, Map<String, String>>,
    @SerialName("user_id")
    val userId: String,
)