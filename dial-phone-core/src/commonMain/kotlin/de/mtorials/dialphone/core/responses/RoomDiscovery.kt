package de.mtorials.dialphone.core.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RoomDiscovery (
    @SerialName("chunk")
    val rooms: List<DiscoveredRoom>,
    @SerialName("total_room_count_estimate")
    val totalRoomCountEstimated: Int
)