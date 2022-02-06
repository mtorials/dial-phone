package de.mtorials.dialphone.api.model.mevents.roommessage.voip

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.HangupReason
import de.mtorials.dialphone.api.model.mevents.roommessage.MatrixMessageEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MessageEventContent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("m.call.hangup")
data class MCallHangup(
    override val sender: UserId,
    override val id: EventId,
    override val content: MCallHangupContent,
) : MatrixMessageEvent {
    @Serializable
    data class MCallHangupContent(
        val reason: HangupReason? = null,
        @SerialName("call.id")
        val callId: String,
        val version: Int,
    ) : MessageEventContent
}