package de.mtorials.dialphone.api.requests.encryption

import de.mtorials.dialphone.api.ids.UserId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeyQueryRequest  (
    /**
     * A map from user ID, to a list of device IDs,
     * or to an empty list to indicate all devices for the corresponding user.
     */
    @SerialName("device_keys")
    val deviceKeys: Map<UserId, List<String>>,
    val token: String? = null,
    val timeout: Int = 100000,
)