package de.mtorials.dialphone.api.responses

import de.mtorials.dialphone.api.ids.RoomId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AliasExchangeResponse(
    @SerialName("room_id")
    val roomId: RoomId,
    val servers: Array<String>
)