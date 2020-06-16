package de.mtorials.dialphone.responses

import com.fasterxml.jackson.annotation.JsonProperty

class EventResponse (
    @JsonProperty("event_id")
    val id: String
)