package de.mtorials.dialphone.api.model.mevents.roommessage.voip

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.VOIPOfferType
import de.mtorials.dialphone.api.model.mevents.roommessage.MatrixMessageEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MessageEventContent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName(MCallInvite.EVENT_TYPE)
data class MCallInvite(
    override val sender: UserId,
    override val id: EventId,
    override val content: MCallInviteContent,
    @SerialName("origin_server_ts")
    override val originServerTs: Long? = null,
) : MatrixMessageEvent {
    @Serializable
    data class MCallInviteContent(
        @SerialName("call.id")
        val callId: String,
        val lifetime: Int,
        val version: Int,
        val offer: Offer,
    ) : MessageEventContent

    @Serializable
    data class Offer(
        val sdp: String,
        val type: VOIPOfferType,
    )

    override fun getTypeName(): String = EVENT_TYPE

    companion object {
        const val EVENT_TYPE = "m.call.invite"
    }
}