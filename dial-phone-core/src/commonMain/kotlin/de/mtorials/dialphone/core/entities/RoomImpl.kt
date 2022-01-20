package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.api.model.enums.JoinRule
import de.mtorials.dialphone.api.model.enums.Membership
import de.mtorials.dialphone.api.model.mevents.roomstate.*
import de.mtorials.dialphone.core.actions.RoomActions
import de.mtorials.dialphone.core.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.core.ids.RoomId
import de.mtorials.dialphone.core.ids.roomId
import de.mtorials.dialphone.core.ids.userId

class RoomImpl(
    action: RoomFutureImpl,
    stateEvents: List<MatrixStateEvent>
) : Room, RoomActions by action {
    override val phone = action.phone

    override var name: String = "init"
    override val members: MutableList<Member> = mutableListOf()
    override val id: RoomId = action.id
    override var avatarUrl: String? = null
    override var joinRule : JoinRule = JoinRule.INVITE

    init {
        for (event in stateEvents) {
            when (event) {
                is MRoomName -> name = event.content.name
                is MRoomJoinRules -> joinRule = event.content.joinRule
                is MRoomAvatar -> avatarUrl = event.content.url
                is MRoomMember -> if (event.content.membership == Membership.JOIN) members.add(
                    MemberImpl(
                        userId = event.stateKey.userId(),
                        phone = phone,
                        roomId = this.id
                    )
                )
            }
        }
    }
}