package de.mtorials.dial.responses

import com.fasterxml.jackson.annotation.JsonProperty

class DiscordedRoomImpl(
    override val name: String,
    override val aliases: List<String> = listOf(),
    @JsonProperty("avatar_url")
    override val avatarURL: String?,
    @JsonProperty("guest_can_join")
    override val guestCanJoin: Boolean,
    @JsonProperty("num_joined_members")
    override val numberMembers: Int,
    override val topic: String?,
    @JsonProperty("world_readable")
    override val worldReadable: Boolean,
    @JsonProperty("room_id")
    override val id: String,
    @JsonProperty("canonical_alias")
    override val canonicalAlias: String
) : DiscoveredRoom