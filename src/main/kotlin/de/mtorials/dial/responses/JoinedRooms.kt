package de.mtorials.dial.responses

import com.fasterxml.jackson.annotation.JsonProperty

class JoinedRooms(
    @JsonProperty("joined_rooms")
    val roomIds: List<String>
)