package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.core.entities.room.JoinedRoom

class MemberImpl(
    user: User,
    override val room: JoinedRoom,
) : Member, User by user {

    override suspend fun forceUpdate() {
        TODO("")
    }
}