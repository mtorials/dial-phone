package de.mtorials.dialphone.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.mt32.makocommons.mevents.MatrixEvent

@Serializable
class RoomStateResponse(
    @SerialName("")
    val stateEvents: List<MatrixEvent>
)