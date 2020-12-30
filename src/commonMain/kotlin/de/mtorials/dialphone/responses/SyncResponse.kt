package de.mtorials.dialphone.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.mt32.makocommons.mevents.MatrixEvent

@Serializable
class SyncResponse(
    @SerialName("presence")
        val presenceEvents: SyncPresence,
    @SerialName("rooms")
        val roomSync: SyncRooms,
    @SerialName("next_batch")
        val nextBatch: String
) {
    
    @Serializable
    class SyncRooms(
            val join: Map<String, RoomsRoom>,
            val invite: Map<String, RoomsInvite>
    ) {
        @Serializable
        class RoomsRoom(
                val timeline: RoomTimeline
        ) {
            @Serializable
            class RoomTimeline(
                val events: List<MatrixEvent>
            )
        }
        @Serializable
        class RoomsInvite(
            @SerialName("invite_state")
            val inviteState: InviteState
        ) {
            @Serializable
            class InviteState(
                val events: List<MatrixEvent>
            )
        }
    }
    @Serializable
    class SyncPresence(
            val events: List<MatrixEvent>
    )
}