package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.model.mevents.ephemeral.MTyping
import de.mtorials.dialphone.core.DialPhoneImpl
import de.mtorials.dialphone.core.entities.Member
import de.mtorials.dialphone.core.entities.room.JoinedRoom

class MemberTypingEvent internal constructor(
    phone: DialPhoneImpl,
    val isTyping: Boolean,
    val room: JoinedRoom,
    val member: Member,
) : DialEvent(
    phone,
    id = null,
)