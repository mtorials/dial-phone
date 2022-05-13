package de.mtorials.dialphone.api.model.mevents.roomstate

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.UserId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName(MRoomCreate.EVENT_TYPE)
@Serializable
class MRoomCreate(
    override val sender: UserId,
    @SerialName("event_id")
    override val id: EventId? = null,
    override val content: Content,
    @SerialName("state_key")
    override val stateKey: String,
    @SerialName("prev_content")
    override val prevContent: Content? = null
) : MatrixStateEvent {
    @Serializable
    data class Content(
        @SerialName("room_version")
        val roomVersion: Int,
        val creator: UserId
    ) : StateEventContent

    override fun getTypeName(): String = EVENT_TYPE
    companion object {
        const val EVENT_TYPE = "m.room.create"
    }
}