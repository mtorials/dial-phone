package de.mtorials.dialphone.api.model.mevents.todevice

import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName(MForwardedRoomKey.EVENT_TYPE)
@Serializable
class MForwardedRoomKey(
    override val content: MForwardedRoomKeyContent,
) : MatrixEvent {
    override val sender: UserId? = null
    @Serializable
    data class MForwardedRoomKeyContent(
        val algorithm: String,
        @SerialName("forwarding_curve25519_key_chain")
        val forwardedCurve25519KeyChain: List<String>,
        @SerialName("room_id")
        val roomId: String,
        @SerialName("sender_claimed_ed25519_key")
        val senderClaimedEd25519Key: String,
        @SerialName("sender_key")
        val senderKey: String,
        @SerialName("session_id")
        val sessionId: String,
        @SerialName("session_key")
        val sessionKey: String,
        val withheld: Map<String, String>? = null,
    ) : EventContent

    override fun getTypeName(): String = EVENT_TYPE

    companion object {
        const val EVENT_TYPE = "m.forwarded_room_key"
    }
}