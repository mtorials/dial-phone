package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.core.entities.room.JoinedRoom

class MemberImpl internal constructor(
    user: User,
    override val room: JoinedRoom,
) : Member, User by user {
    override val avatarURL: String?
        get() = room.members.filter { it.id == id }.getOrNull(0)?.avatarURL
    override val displayName: String?
        get() = room.members.filter { it.id == id }.getOrNull(0)?.displayName

}