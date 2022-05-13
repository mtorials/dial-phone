package de.mtorials.dialphone.api.responses.sync

import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
data class StateResponse(
    val events: List<JsonObject> = listOf(),
)