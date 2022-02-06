package de.mtorials.dialphone.api.model.mevents.roommessage.voip

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.VOIPSessionDescription
import de.mtorials.dialphone.api.model.mevents.roommessage.MatrixMessageEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MessageEventContent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("m.call.answer")
data class MCallAnswer(
    override val sender: UserId,
    override val id: EventId,
    override val content: MCallAnswerContent,
) : MatrixMessageEvent {
    @Serializable
    data class MCallAnswerContent(
        val answer: Answer,
        @SerialName("call.id")
        val callId: String,
        val version: Int,
    ) : MessageEventContent

    @Serializable
    data class Answer(
        val sdp: String,
        val type: VOIPSessionDescription,
    )
}