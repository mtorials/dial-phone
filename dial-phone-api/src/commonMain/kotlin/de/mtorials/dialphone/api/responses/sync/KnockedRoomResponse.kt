package de.mtorials.dialphone.api.responses.sync

import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KnockedRoomResponse(
    @SerialName("knock_state")
    val knockState: KnockState,
) {
    @Serializable
    data class KnockState(
        val events: List<MatrixStateEvent> = listOf(),
    )
}