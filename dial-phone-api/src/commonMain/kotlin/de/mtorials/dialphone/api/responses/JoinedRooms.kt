package de.mtorials.dialphone.api.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class JoinedRooms(
    @SerialName("joined_rooms")
    val roomIds: List<String>
)