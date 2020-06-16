package de.mtorials.dialphone.responses

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import de.mtorials.dialphone.mevents.MatrixEvent

@JsonIgnoreProperties(ignoreUnknown = true)
class SyncResponse(
    @JsonProperty("presence")
        val presenceEvents: SyncPresence,
    @JsonProperty("rooms")
        val roomSync: SyncRooms,
    @JsonProperty("next_batch")
        val nextBatch: String
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    class SyncRooms(
            val join: Map<String, RoomsRoom>
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        class RoomsRoom(
                val timeline: RoomTimeline
        ) {
            @JsonIgnoreProperties(ignoreUnknown = true)
            class RoomTimeline(
                    val events: List<MatrixEvent>
            )
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class SyncPresence(
            val events: List<MatrixEvent>
    )
}