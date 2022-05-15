package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember
import de.mtorials.dialphone.core.DialPhoneImpl
import de.mtorials.dialphone.core.entities.room.JoinedRoomImpl

class MemberImpl internal constructor(
    user: User,
    override val room: JoinedRoomImpl,
    override val phone: DialPhoneImpl,
) : Member, User by user {

    override val avatarURL: String?
        get() = phone.cache.state.getRoomStateEvents<MRoomMember>(room.id, MRoomMember.EVENT_TYPE)
            .filter { it.stateKey == id.toString() }.getOrNull(0)?.content?.avatarURL

    override val displayName: String?
        get() = phone.cache.state.getRoomStateEvents<MRoomMember>(room.id, MRoomMember.EVENT_TYPE)
            .filter { it.stateKey == id.toString() }.getOrNull(0)?.content?.displayName
}