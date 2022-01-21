package de.mtorials.dialphone.api.responses.sync

import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class JoinedRoomResponse(
    @SerialName("account_data")
    val accountData: AccountData? = null,
    val ephemeral: Ephemeral? = null,
    val timeline: RoomTimeline,
    val state: StateResponse? = null,
    val summery: SummeryResponse? = null,
    val unreadNotifications: UnreadNotificationsResponse? = null,
)