package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.core.DialPhoneCore
import de.mtorials.dialphone.core.model.mevents.roomstate.MRoomMember

class RoomInviteEvent(
    val invitedRoomActions: de.mtorials.dialphone.core.entities.actions.InvitedRoomActions,
    val senderId: String,
    val content: MRoomMember.Content,
    override val phone: DialPhoneCore
) : DialEvent(phone) {
    constructor(roomId: String, event: MRoomMember, phone: DialPhoneCore) : this(
        de.mtorials.dialphone.core.entities.actions.InvitedRoomActionsImpl(
            phone,
            roomId
        ), event.sender, event.content, phone)
}