package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.actions.InvitedRoomActions
import de.mtorials.dialphone.core.actions.InvitedRoomActionsImpl

class RoomInviteEvent(
    val invitedRoomActions: InvitedRoomActions,
    val senderId: String,
    val content: MRoomMember.Content,
    override val phone: DialPhoneApi
) : DialEvent(phone) {
    constructor(roomId: String, event: MRoomMember, phone: DialPhone) : this(
        InvitedRoomActionsImpl(
            phone,
            roomId
        ), event.sender, event.content, phone)
}