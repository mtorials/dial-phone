package de.mtorials.dial.responses

import com.fasterxml.jackson.annotation.JsonProperty

class DisplayName(
    @JsonProperty("display_name")
    val name: String
)