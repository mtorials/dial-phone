package de.mtorials.dialphone.api.responses.sync

import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.Serializable

@Serializable
class RoomTimeline(
    val events: List<MatrixEvent>,
    val limited: Boolean = false,
    val prev: String? = null,
)