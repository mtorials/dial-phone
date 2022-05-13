package de.mtorials.dialphone.api.model.mevents.ephemeral

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName(MTyping.EVENT_TYPE)
data class MTyping(
    override val content: MTypingContent,
    @SerialName("room_id")
    val roomId: RoomId,
    override val sender: UserId? = null,
) : MatrixEvent {

    @Serializable
    data class MTypingContent(
        @SerialName("user_ids")
        val userIds: List<UserId>,
    ) : EventContent

    override fun getTypeName(): String = EVENT_TYPE

    companion object {
        const val EVENT_TYPE = "m.call.candidates"
    }
}