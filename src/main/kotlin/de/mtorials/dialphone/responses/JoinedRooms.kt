package de.mtorials.dialphone.responses

import com.fasterxml.jackson.annotation.JsonProperty

class JoinedRooms(
    @JsonProperty("joined_rooms")
    val roomIds: List<String>
)