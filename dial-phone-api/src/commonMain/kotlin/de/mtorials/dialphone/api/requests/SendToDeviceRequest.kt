package de.mtorials.dialphone.api.requests

import de.mtorials.dialphone.api.ids.UserId
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * https://spec.matrix.org/v1.1/client-server-api/#put_matrixclientv3sendtodeviceeventtypetxnid
 */
@Serializable
data class SendToDeviceRequest(
    /**
     * userid to deviceId to content
     */
    val messages: Map<UserId, Map<String, JsonElement>>
)