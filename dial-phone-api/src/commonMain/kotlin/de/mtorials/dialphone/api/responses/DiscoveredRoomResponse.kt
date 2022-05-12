package de.mtorials.dialphone.api.responses

import de.mtorials.dialphone.api.ids.RoomAlias
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.enums.JoinRule


interface DiscoveredRoomResponse {
    val id: RoomId
    val name: String?
    val aliases: List<RoomAlias>
    val canonicalAlias: RoomAlias?
    val avatarURL: String?
    val joinRule: JoinRule?
    val guestCanJoin: Boolean
    val numberMembers: Int
    val topic: String?
    val worldReadable: Boolean
}