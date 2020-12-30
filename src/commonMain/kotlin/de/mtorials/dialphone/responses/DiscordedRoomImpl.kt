package de.mtorials.dialphone.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DiscordedRoomImpl(
    override val name: String,
    override val aliases: List<String> = listOf(),
    @SerialName("avatar_url")
    override val avatarURL: String?,
    @SerialName("guest_can_join")
    override val guestCanJoin: Boolean,
    @SerialName("num_joined_members")
    override val numberMembers: Int,
    override val topic: String?,
    @SerialName("world_readable")
    override val worldReadable: Boolean,
    @SerialName("room_id")
    override val id: String,
    @SerialName("canonical_alias")
    override val canonicalAlias: String
) : DiscoveredRoom