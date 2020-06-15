package de.mtorials.dial.entities

import de.mtorials.dial.entityfutures.RoomFuture
import de.mtorials.dial.entityfutures.UserFuture
import de.mtorials.dial.enums.JoinRule
import de.mtorials.dial.enums.Membership
import de.mtorials.dial.mevents.room.MRoomJoinRules
import de.mtorials.dial.mevents.room.MRoomMember
import de.mtorials.dial.mevents.room.MRoomName
import de.mtorials.dial.mevents.MatrixEvent
import de.mtorials.dial.mevents.room.MRoomAvatar

class RoomImpl(
    private val action: RoomFuture,
    stateEvents: Array<MatrixEvent>
) : Room {
    override val phone = action.phone
    override var name: String = "init"
    override val members: MutableList<Member> = mutableListOf()
    override val id: String = action.entityId
    override var avatarUrl: String? = null
    override var joinRule : JoinRule = JoinRule.INVITE

    init {
        for (event in stateEvents) {
            when (event) {
                is MRoomName -> name = event.content.name
                is MRoomJoinRules -> joinRule = event.content.joinRule
                is MRoomAvatar -> avatarUrl = event.content.url
                is MRoomMember -> if (event.content.membership == Membership.JOIN) members.add(MemberImpl(
                    user = UserFuture(event.sender, phone),
                    displayName = event.content.displayName,
                    avatarUrl = event.content.avatarURL,
                    phone = phone
                ))
            }
        }
    }

}