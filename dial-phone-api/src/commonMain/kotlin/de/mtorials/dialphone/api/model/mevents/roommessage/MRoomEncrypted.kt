package de.mtorials.dialphone.api.model.mevents.roommessage

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.MessageEncryptionAlgorithm
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.MatrixRoomEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@SerialName(MRoomEncrypted.EVENT_TYPE)
@Serializable()
class MRoomEncrypted(
    override val sender: UserId,
    @SerialName("event_id")
    val id: EventId? = null,
    override val content: MRoomEncryptedContent,
    @SerialName("origin_server_ts")
    override val originServerTs: Long? = null,
) : MatrixRoomEvent {
    @Serializable
    data class MRoomEncryptedContent(
        @SerialName("sender_key")
        val senderKey: String? = null,
        @SerialName("ciphertext")
        // can be string with megolm, can be object with olm
        val cipherText: JsonElement,
        @SerialName("session_id")
        val sessionId: String? = null,
        @SerialName("device_id")
        val deviceId: String? = null,
        val algorithm: MessageEncryptionAlgorithm,
    ) : MessageEventContent

    override fun getTypeName(): String = EVENT_TYPE

    companion object {
        const val EVENT_TYPE = "m.room.encrypted"
    }
}