package de.mtorials.dialphone.api.requests.encryption

import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.MessageEncryptionAlgorithm
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * https://spec.matrix.org/v1.1/client-server-api/#key-management-api
 */
@Serializable
open class DeviceKeys(
    open val algorithms: List<MessageEncryptionAlgorithm>,
    @SerialName("device_id")
    open val deviceId: String,
    /**
     * Public identity keys.
     * The names of the properties should be in the format <algorithm>:<device_id>.
     * The keys themselves should be encoded as specified by the key algorithm.
     */
    open val keys: Map<String, String>,
    @SerialName("user_id")
    open val userId: UserId,
)