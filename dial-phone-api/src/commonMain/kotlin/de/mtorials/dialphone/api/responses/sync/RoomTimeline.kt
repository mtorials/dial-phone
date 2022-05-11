package de.mtorials.dialphone.api.responses.sync

import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
class RoomTimeline(
    val events: List<JsonObject>,
    val limited: Boolean = false,
    val prev: String? = null,
)