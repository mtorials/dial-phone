package de.mtorials.dialphone.api.responses.sync

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SummeryResponse(
    @SerialName("m.heroes")
    val heroes: List<String> = listOf(),
    @SerialName("m.invited_member_count")
    val invitedMemberCount: Int? = null,
    @SerialName("m.joined_member_count")
    val joinedMemberCount: Int? = null,
)