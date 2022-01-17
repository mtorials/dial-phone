package de.mtorials.dialphone.responses


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