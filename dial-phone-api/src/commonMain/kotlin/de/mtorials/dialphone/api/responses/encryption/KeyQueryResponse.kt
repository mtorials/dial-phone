package de.mtorials.dialphone.api.responses.encryption

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeyQueryResponse(
    /**
     * A map from user ID, to a map from device ID to device information
     */
    @SerialName("device_keys")
    val deviceKeys: Map<String, Map<String, String>>? = null,
    val failures: String? = null,
    /**
     * A map from user ID, to master key information.
     */
    @SerialName("master_keys")
    val masterKeys: Map<String, String>? = null,
    /**
     * A map from user ID, to self-signing key information
     */
    @SerialName("self_signing_keys")
    val selfSigningKeys: Map<String, String>? = null,
    /**
     *  A map from user ID, to user-signing key information.
     */
    @SerialName("user_signing_keys")
    val userSigningKeys: Map<String, String>? = null,
)