package de.mtorials.dialphone.responses

import com.fasterxml.jackson.annotation.JsonProperty

class RoomResponse (
    @JsonProperty("room_id")
    val id: String
)