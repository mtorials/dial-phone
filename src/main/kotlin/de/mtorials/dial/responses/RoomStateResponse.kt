package de.mtorials.dial.responses

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import de.mtorials.dial.mevents.MatrixEvent

@JsonIgnoreProperties(ignoreUnknown = true)
class RoomStateResponse(
    @JsonProperty("")
    val stateEvents: List<MatrixEvent>
)