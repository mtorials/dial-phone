package de.mtorials.dial.responses

import com.fasterxml.jackson.annotation.JsonProperty

class RoomDiscovery (
    @JsonProperty("chunk")
    val rooms: List<DiscoveredRoom>,
    @JsonProperty("total_room_count_estimate")
    val totalRoomCountEstimated: Int
)