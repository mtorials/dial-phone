package de.mtorials.dialphone.api.model.mevents.todevice

import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.RequestedKeyInfo
import de.mtorials.dialphone.api.model.enums.KeyRequestAction
import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName(MRoomKeyRequest.EVENT_TYPE)
data class MRoomKeyRequest(
    override val content: MRoomKeyRequestContent,
) : MatrixEvent {
    override val sender: UserId? = null
    @Serializable
    data class MRoomKeyRequestContent(
        val action: KeyRequestAction,
        val body: RequestedKeyInfo? = null,
        @SerialName("request_id")
        val requestId: String,
        @SerialName("requesting_device_id")
        val requestingDeviceId: String,
    ) : EventContent

    override fun getTypeName(): String = EVENT_TYPE

    companion object {
        const val EVENT_TYPE = "m.room_key_request"
    }
}