package de.mtorials.dialphone.model.mevents.roomstate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SerialName("m.room.create")
@Serializable
class MRoomCreate(
    override val sender: String,
    @SerialName("event_id")
    override val id: String? = null,
    override val content: Content,
    @SerialName("state_key")
    override val stateKey: String,
    @SerialName("prev_content")
    override val prevContent: Content? = null
) : MatrixStateEvent {
    @Serializable
    data class Content(
        @SerialName("room_version")
        @Serializable
        val roomVersion: Int,
        val creator: String
    ) : StateEventContent
}