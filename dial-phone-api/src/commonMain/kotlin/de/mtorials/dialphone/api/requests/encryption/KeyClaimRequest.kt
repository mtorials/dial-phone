package de.mtorials.dialphone.api.requests.encryption

import de.mtorials.dialphone.api.ids.UserId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeyClaimRequest(
    /**
     * The keys to be claimed. A map from user ID, to a map from device ID to algorithm name.
     */
    @SerialName("one_time_keys")
    val onTimeKeys: Map<UserId, Map<String, String>>,
    val timeout: Int = 100000,
)