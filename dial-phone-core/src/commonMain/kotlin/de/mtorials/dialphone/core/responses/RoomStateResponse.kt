package de.mtorials.dialphone.core.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import de.mtorials.dialphone.core.model.mevents.MatrixEvent

@Serializable
class RoomStateResponse(
    @SerialName("")
    val stateEvents: List<MatrixEvent>
)