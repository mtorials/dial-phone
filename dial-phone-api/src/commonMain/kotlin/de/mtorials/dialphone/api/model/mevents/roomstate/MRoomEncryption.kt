package de.mtorials.dialphone.api.model.mevents.roomstate

import de.mtorials.dialphone.api.model.enums.RoomEncryptionAlgorithm
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("m.room.encryption")
@Serializable
class MRoomEncryption(
    override val sender: String,
    @SerialName("event_id")
    override val id: String? = null,
    override val content: Content,
    @SerialName("state_key")
    override val stateKey: String,
    @SerialName("prev_content")
    override val prevContent: Content? = null
) : MatrixStateEvent {
    @Serializable
    data class Content(
        val algorithm: RoomEncryptionAlgorithm,
    ) : StateEventContent
}