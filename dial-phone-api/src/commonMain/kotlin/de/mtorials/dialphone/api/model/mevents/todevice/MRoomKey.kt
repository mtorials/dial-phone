package de.mtorials.dialphone.api.model.mevents.todevice

import de.mtorials.dialphone.api.model.enums.RoomEncryptionAlgorithm
import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("m.room_key")
@Serializable
data class MRoomKey(
    override val content: MRoomKeyContent,
) : MatrixEvent {
    override val sender: String? = null
    @Serializable
    data class MRoomKeyContent(
        val algorithm: RoomEncryptionAlgorithm,
        @SerialName("room_id")
        val roomId: String,
        @SerialName("session_id")
        val sessionId: String,
        @SerialName("session_key")
        val sessionKey: String,
    ) : EventContent
}