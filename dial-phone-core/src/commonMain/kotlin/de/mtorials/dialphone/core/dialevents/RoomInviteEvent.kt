package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.core.entities.room.InvitedRoom

class RoomInviteEvent(
    override val phone: DialPhone,
    val room: InvitedRoom,
    val sender: User,
    val content: MRoomMember.Content,
) : DialEvent(phone) {
//    constructor(roomId: RoomId, event: MRoomMember, phone: DialPhone) : this(
//        room = RoomFutureImpl(roomId, phone),
//        actions = InvitedRoomActionsImpl(
//            phone,
//            roomId
//        ),
//        sender = UserFutureImpl(event.sender, phone),
//        content = event.content,
//        phone = phone
//    )
}