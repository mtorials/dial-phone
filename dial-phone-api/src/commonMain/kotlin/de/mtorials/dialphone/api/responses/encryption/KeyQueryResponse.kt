package de.mtorials.dialphone.api.responses.encryption

import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.requests.encryption.SignedDeviceKeys
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

@Serializable
data class KeyQueryResponse(
    /**
     * A map from user ID, to a map from device ID to device information
     */
    @SerialName("device_keys")
    val deviceKeys: Map<UserId, Map<String, SignedDeviceKeys>>? = null,
    val failures: JsonElement? = null,
    /**
     * A map from user ID, to master key information.
     */
    @SerialName("master_keys")
    val masterKeys: Map<String, JsonElement>? = null,
    /**
     * A map from user ID, to self-signing key information
     */
    @SerialName("self_signing_keys")
    val selfSigningKeys: Map<String, JsonElement>? = null,
    /**
     *  A map from user ID, to user-signing key information.
     */
    @SerialName("user_signing_keys")
    val userSigningKeys: Map<String, JsonElement>? = null,
)