package de.mtorials.dialphone.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AliasExchangeResponse(
    @SerialName("room_id")
    val roomId: String,
    val servers: Array<String>
)