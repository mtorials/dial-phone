package de.mtorials.dialphone.dialevents

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.actions.InvitedRoomActions
import de.mtorials.dialphone.entities.actions.InvitedRoomActionsImpl
import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.mevents.roomstate.MRoomMember

class RoomInviteEvent(
    val invitedRoomActions: InvitedRoomActions,
    val senderId: String,
    val content: MRoomMember.Content,
    override val phone: DialPhone
) : DialEvent(phone) {
    constructor(roomId: String, event: MRoomMember, phone: DialPhone) : this(InvitedRoomActionsImpl(phone, roomId), event.sender, event.content, phone)
}