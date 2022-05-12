package de.mtorials.dialphone.api.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RoomDiscovery (
    @SerialName("chunk")
    val rooms: List<DiscoveredRoomResponse>,
    @SerialName("total_room_count_estimate")
    val totalRoomCountEstimated: Int
)