package de.mtorials.dialphone.api.model.mevents.roommessage.voip

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.mevents.roommessage.MatrixMessageEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MessageEventContent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName(MCallCandidates.EVENT_TYPE)
data class MCallCandidates(
    override val sender: UserId,
    override val id: EventId,
    override val content: MCallCandidatesContent,
    @SerialName("origin_server_ts")
    override val originServerTs: Long? = null,
) : MatrixMessageEvent {
    @Serializable
    data class MCallCandidatesContent(
        val candidates: List<Candidate>,
        @SerialName("call.id")
        val callId: String,
        val version: Int,
    ) : MessageEventContent

    @Serializable
    data class Candidate(
        val candidate: String,
        val sdpMLineIndex: Int,
        val sdpMid: String,
    )

    override fun getTypeName(): String = EVENT_TYPE

    companion object {
        const val EVENT_TYPE = "m.call.candidates"
    }
}