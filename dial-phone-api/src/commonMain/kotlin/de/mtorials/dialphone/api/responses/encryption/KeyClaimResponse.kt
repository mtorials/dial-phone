package de.mtorials.dialphone.api.responses.encryption

import de.mtorials.dialphone.api.ids.UserId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * https://spec.matrix.org/v1.1/client-server-api/#post_matrixclientv3keysclaim
 */
@Serializable
data class KeyClaimResponse(
    val failures: JsonElement? = null,
    /**
     * One-time keys for the queried devices.
     * A map from user ID, to a map from devices to a map from <algorithm>:<key_id> to the key object.
     */
    @SerialName("one_time_keys")
    val oneTimeKeys: Map<UserId, Map<String, JsonElement>>
)
