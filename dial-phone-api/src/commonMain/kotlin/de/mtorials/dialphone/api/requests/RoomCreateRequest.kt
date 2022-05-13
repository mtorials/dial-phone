package de.mtorials.dialphone.api.requests

import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.RoomVisibility
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * See spec.matrix.org
 */
@Serializable
data class RoomCreateRequest(
    @SerialName("initial_state")
    val initialState: List<MatrixStateEvent>,
    val invite: List<UserId>,
    val name: String,
    val visibility: RoomVisibility,
    @SerialName("is_direct")
    val isDirect: Boolean,
    val topic: String? = null,
    @SerialName("room_alias_name")
    val alias: String? = null
)