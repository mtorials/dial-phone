package de.mtorials.dialphone.api.responses

import de.mtorials.dialphone.api.ids.RoomAlias
import de.mtorials.dialphone.api.ids.RoomId


interface DiscoveredRoom {
    val id: RoomId
    val name: String
    val aliases: List<RoomAlias>
    val canonicalAlias: RoomAlias
    val avatarURL: String?
    val guestCanJoin: Boolean
    val numberMembers: Int
    val topic: String?
    val worldReadable: Boolean
}