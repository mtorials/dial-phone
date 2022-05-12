package de.mtorials.dialphone.api.responses

import de.mtorials.dialphone.api.ids.RoomAlias
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.enums.JoinRule
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscordedRoomResponseImpl(
    override val name: String? = null,
    override val aliases: List<RoomAlias> = listOf(),
    @SerialName("join_rule")
    override val joinRule: JoinRule? = null,
    @SerialName("avatar_url")
    override val avatarURL: String? = null,
    @SerialName("guest_can_join")
    override val guestCanJoin: Boolean,
    @SerialName("num_joined_members")
    override val numberMembers: Int,
    override val topic: String? = null,
    @SerialName("world_readable")
    override val worldReadable: Boolean,
    @SerialName("room_id")
    override val id: RoomId,
    // TODO check if required
    @SerialName("canonical_alias")
    override val canonicalAlias: RoomAlias? = null,
) : DiscoveredRoomResponse {
}