package de.mtorials.dialphone.api.requests.encryption

import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.MessageEncryptionAlgorithm
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SignedDeviceKeys(
    /**
     * Signatures for the device key object. A map from user ID, to a map from <algorithm>:<device_id> to the signature.
     * The signature is calculated using the process described at Signing JSON.
     */
    val signatures: Map<UserId, Map<String, String>>,
    val algorithms: List<MessageEncryptionAlgorithm>,
    @SerialName("device_id")
    val deviceId: String,
    val keys: Map<String, String>,
    @SerialName("user_id")
    val userId: UserId,
) {
    constructor(deviceKeys: DeviceKeys, signatures: Map<UserId, Map<String, String>>) : this(
        algorithms = deviceKeys.algorithms,
        deviceId = deviceKeys.deviceId,
        keys = deviceKeys.keys,
        userId = deviceKeys.userId,
        signatures = signatures,
    )
}

