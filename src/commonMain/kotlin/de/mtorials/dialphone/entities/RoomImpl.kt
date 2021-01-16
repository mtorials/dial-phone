package de.mtorials.dialphone.entities

import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.model.enums.JoinRule
import de.mtorials.dialphone.model.enums.Membership
import de.mtorials.dialphone.model.mevents.roommessage.MessageEventContent
import de.mtorials.dialphone.model.mevents.roomstate.*

class RoomImpl(
    action: RoomFutureImpl,
    stateEvents: Array<MatrixStateEvent>
) : Room {
    override val phone = action.phone

    override var name: String = "init"
    override val members: MutableList<Member> = mutableListOf()
    override val id: String = action.id
    override var avatarUrl: String? = null
    override var joinRule : JoinRule = JoinRule.INVITE

    init {
        for (event in stateEvents) {
            when (event) {
                is MRoomName -> name = event.content.name
                is MRoomJoinRules -> joinRule = event.content.joinRule
                is MRoomAvatar -> avatarUrl = event.content.url
                is MRoomMember -> if (event.content.membership == Membership.JOIN) members.add(MemberImpl(
                    id = event.stateKey,
                    phone = phone,
                    roomId = this.id
                ))
            }
        }
    }

    override suspend fun sendMessageEvent(content: MessageEventContent, eventType: String): String =
        RoomFutureImpl(id, phone).sendMessageEvent(content, eventType)

    override suspend fun sendStateEvent(content: StateEventContent, eventType: String, stateKey: String): String =
        RoomFutureImpl(id, phone).sendStateEvent(content, eventType, stateKey)
}