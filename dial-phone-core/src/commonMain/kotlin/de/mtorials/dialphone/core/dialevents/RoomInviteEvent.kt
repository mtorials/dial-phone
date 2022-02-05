package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.actions.InvitedRoomActions
import de.mtorials.dialphone.core.actions.InvitedRoomActionsImpl
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.core.entityfutures.RoomFuture
import de.mtorials.dialphone.core.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.core.entityfutures.UserFuture
import de.mtorials.dialphone.core.entityfutures.UserFutureImpl

class RoomInviteEvent(
    val room: RoomFuture,
    val actions: InvitedRoomActions,
    val sender: UserFuture,
    val content: MRoomMember.Content,
    override val phone: DialPhone
) : DialEvent(phone) {
    constructor(roomId: RoomId, event: MRoomMember, phone: DialPhone) : this(
        room = RoomFutureImpl(roomId, phone),
        actions = InvitedRoomActionsImpl(
            phone,
            roomId
        ),
        sender = UserFutureImpl(event.sender, phone),
        content = event.content,
        phone = phone
    )
}