package de.mtorials.dialphone.api.responses.sync

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import de.mtorials.dialphone.api.model.mevents.presence.MPresence

@Serializable
class SyncResponse(
    @SerialName("presence")
    val presenceEvents: SyncPresence? = null,
    @SerialName("rooms")
    val roomSync: SyncRooms? = null,
    @SerialName("next_batch")
    val nextBatch: String? = null,
) {
    
    @Serializable
    class SyncRooms(
        val join: Map<String, JoinedRoomResponse> = mutableMapOf(),
        val invite: Map<String, InvitedRoomResponse> = mutableMapOf(),
        val leave: Map<String, LeftRoomResponse> = mutableMapOf(),
        val knock: Map<String, KnockedRoomResponse> = mutableMapOf(),
    )
    @Serializable
    class SyncPresence(
        val events: List<MPresence>
    )
}