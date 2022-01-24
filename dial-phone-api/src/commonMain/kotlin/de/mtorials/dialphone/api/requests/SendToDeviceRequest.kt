package de.mtorials.dialphone.api.requests

import de.mtorials.dialphone.api.model.mevents.EventContent
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

/**
 * https://spec.matrix.org/v1.1/client-server-api/#put_matrixclientv3sendtodeviceeventtypetxnid
 */
@Serializable
data class SendToDeviceRequest(
    /**
     * userid to deviceId to content
     */
    val messages: Map<String, Map<String, EventContent>>
)