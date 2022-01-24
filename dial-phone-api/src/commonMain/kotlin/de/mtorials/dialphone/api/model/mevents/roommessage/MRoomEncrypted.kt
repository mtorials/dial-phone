package de.mtorials.dialphone.api.model.mevents.roommessage

import de.mtorials.dialphone.api.model.enums.MessageEncryptionAlgorithm
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO check serializer for Pair
@SerialName("m.room.encrypted")
@Serializable
class MRoomEncrypted(
    override val sender: String,
    @SerialName("event_id")
    override val id: String,
    override val content: MRoomEncryptedContent
) : MatrixMessageEvent {
    @Serializable
    data class MRoomEncryptedContent(
        @SerialName("sender_key")
        val senderKey: String? = null,
        @SerialName("ciphertext")
        // can be string with megolm, can be object with olm
        val cipherText: String,
        @SerialName("session_id")
        val sessionId: String? = null,
        @SerialName("device_id")
        val deviceId: String? = null,
        val algorithm: MessageEncryptionAlgorithm
    ) : MessageEventContent
}