package de.mtorials.dialphone.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import de.mtorials.dialphone.model.mevents.MatrixEvent

@Serializable
class RoomStateResponse(
    @SerialName("")
    val stateEvents: List<MatrixEvent>
)