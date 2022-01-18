package de.mtorials.dialphone.api.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RoomResponse (
    @SerialName("room_id")
    val id: String
)