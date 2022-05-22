package de.mtorials.dialphone.api.model.mevents.roommessage.voip

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.VOIPSessionDescription
import de.mtorials.dialphone.api.model.mevents.roommessage.MatrixMessageEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MessageEventContent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName(MCallAnswer.EVENT_TYPE)
data class MCallAnswer(
    override val sender: UserId,
    override val id: EventId,
    override val content: MCallAnswerContent,
    @SerialName("origin_server_ts")
    override val originServerTs: Long? = null,
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

    override fun getTypeName(): String = EVENT_TYPE

    companion object {
        const val EVENT_TYPE = "m.call.answer"
    }
}