package de.mtorials.dialphone.responses

import com.fasterxml.jackson.annotation.JsonProperty

class AliasExchangeResponse(
    @JsonProperty("room_id")
    val roomId: String,
    val servers: Array<String>
)