package de.mtorials.dialphone.api.responses

import de.mtorials.dialphone.api.ids.RoomId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class JoinedRooms(
    @SerialName("joined_rooms")
    val roomIds: List<RoomId>
)