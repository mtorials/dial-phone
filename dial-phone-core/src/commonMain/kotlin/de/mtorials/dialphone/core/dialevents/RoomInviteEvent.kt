package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.model.mevents.roomstate.MRoomMember

class RoomInviteEvent(
    val invitedRoomActions: de.mtorials.dialphone.core.entities.actions.InvitedRoomActions,
    val senderId: String,
    val content: MRoomMember.Content,
    override val phone: DialPhone
) : DialEvent(phone) {
    constructor(roomId: String, event: MRoomMember, phone: DialPhone) : this(
        de.mtorials.dialphone.core.entities.actions.InvitedRoomActionsImpl(
            phone,
            roomId
        ), event.sender, event.content, phone)
}