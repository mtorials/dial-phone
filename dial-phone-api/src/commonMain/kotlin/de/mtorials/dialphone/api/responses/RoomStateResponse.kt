package de.mtorials.dialphone.api.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import de.mtorials.dialphone.api.model.mevents.MatrixEvent

@Serializable
class RoomStateResponse(
    @SerialName("")
    val stateEvents: List<MatrixEvent>
)