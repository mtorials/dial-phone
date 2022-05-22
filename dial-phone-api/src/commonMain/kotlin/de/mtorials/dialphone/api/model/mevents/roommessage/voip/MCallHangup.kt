package de.mtorials.dialphone.api.model.mevents.roommessage.voip

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.HangupReason
import de.mtorials.dialphone.api.model.mevents.roommessage.MatrixMessageEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MessageEventContent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName(MCallHangup.EVENT_TYPE)
data class MCallHangup(
    override val sender: UserId,
    override val id: EventId,
    override val content: MCallHangupContent,
    @SerialName("origin_server_ts")
    override val originServerTs: Long? = null,
) : MatrixMessageEvent {
    @Serializable
    data class MCallHangupContent(
        val reason: HangupReason? = null,
        @SerialName("call.id")
        val callId: String,
        val version: Int,
    ) : MessageEventContent

    override fun getTypeName(): String = EVENT_TYPE

    companion object {
        const val EVENT_TYPE = "m.call.hangup"
    }
}