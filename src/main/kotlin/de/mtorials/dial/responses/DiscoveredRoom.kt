package de.mtorials.dial.responses

import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(`as` = DiscordedRoomImpl::class)
interface DiscoveredRoom {
    val id: String
    val name: String
    val aliases: List<String>
    val canonicalAlias: String
    val avatarURL: String?
    val guestCanJoin: Boolean
    val numberMembers: Int
    val topic: String?
    val worldReadable: Boolean
}