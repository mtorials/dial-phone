package de.mtorials.dialphone.api.model.mevents.roommessage.voip

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.mevents.roommessage.MatrixMessageEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MessageEventContent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("m.call.candidates")
data class MCallCandidates(
    override val sender: UserId,
    override val id: EventId,
    override val content: MCallCandidatesContent,
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
}