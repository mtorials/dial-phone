package de.mtorials.dialphone.api.responses.sync

import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import kotlinx.serialization.Serializable

@Serializable
data class StateResponse(
    val events: List<MatrixStateEvent> = listOf(),
)