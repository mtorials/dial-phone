package de.mtorials.dialphone.responses

import com.fasterxml.jackson.annotation.JsonProperty

class DisplayNameResponse (
    @JsonProperty("displayname")
    val displayName: String
)