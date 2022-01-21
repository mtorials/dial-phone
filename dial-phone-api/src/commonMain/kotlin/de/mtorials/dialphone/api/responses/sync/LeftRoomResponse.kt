package de.mtorials.dialphone.api.responses.sync

import kotlinx.serialization.Serializable

@Serializable
data class LeftRoomResponse(
    val accountData: AccountData? = null,
    val state: StateResponse? = null,
    val timeline: RoomTimeline? = null,
)