package de.mtorials.dialphone.api.responses.sync

import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
class InvitedRoomResponse(
    @SerialName("invite_state")
    val inviteState: InviteState,
) {
    @Serializable
    class InviteState(
        val events: List<JsonObject>
    )
}