package de.mtorials.dialphone.responses

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import net.micromes.makocommons.mevents.MatrixEvent

@JsonIgnoreProperties(ignoreUnknown = true)
class RoomStateResponse(
    @JsonProperty("")
    val stateEvents: List<MatrixEvent>
)