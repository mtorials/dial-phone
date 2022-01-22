package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.core.ids.UserId
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.actions.InvitedRoomActions
import de.mtorials.dialphone.core.actions.InvitedRoomActionsImpl
import de.mtorials.dialphone.core.ids.RoomId
import de.mtorials.dialphone.core.ids.userId

class RoomInviteEvent(
    val invitedRoomActions: InvitedRoomActions,
    val senderId: UserId,
    val content: MRoomMember.Content,
    override val phone: DialPhone
) : DialEvent(phone) {
    constructor(roomId: RoomId, event: MRoomMember, phone: DialPhone) : this(
        InvitedRoomActionsImpl(
            phone,
            roomId
        ), event.sender.userId(), event.content, phone)
}