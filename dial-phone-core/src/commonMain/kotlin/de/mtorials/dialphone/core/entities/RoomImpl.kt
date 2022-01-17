package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.core.model.enums.JoinRule
import de.mtorials.dialphone.core.model.enums.Membership
import de.mtorials.dialphone.core.model.mevents.roomstate.*

class RoomImpl(
    action: de.mtorials.dialphone.core.entities.entityfutures.RoomFutureImpl,
    stateEvents: List<MatrixStateEvent>
) : de.mtorials.dialphone.core.entities.Room, de.mtorials.dialphone.core.entities.actions.RoomActions by action {
    override val phone = action.phone

    override var name: String = "init"
    override val members: MutableList<de.mtorials.dialphone.core.entities.Member> = mutableListOf()
    override val id: String = action.id
    override var avatarUrl: String? = null
    override var joinRule : JoinRule = JoinRule.INVITE

    init {
        for (event in stateEvents) {
            when (event) {
                is MRoomName -> name = event.content.name
                is MRoomJoinRules -> joinRule = event.content.joinRule
                is MRoomAvatar -> avatarUrl = event.content.url
                is MRoomMember -> if (event.content.membership == Membership.JOIN) members.add(
                    de.mtorials.dialphone.core.entities.MemberImpl(
                        id = event.stateKey,
                        phone = phone,
                        roomId = this.id
                    )
                )
            }
        }
    }
}