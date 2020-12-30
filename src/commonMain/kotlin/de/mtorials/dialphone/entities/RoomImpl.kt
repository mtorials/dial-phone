package de.mtorials.dialphone.entities

import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl
import net.mt32.makocommons.enums.JoinRule
import net.mt32.makocommons.enums.Membership
import net.mt32.makocommons.mevents.roommessage.MessageEventContent
import net.mt32.makocommons.mevents.roomstate.*

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

    override suspend fun sendMessageEvent(content: MessageEventContent): String =
        RoomFutureImpl(id, phone).sendMessageEvent(content)

    override suspend fun sendStateEvent(content: StateEventContent, stateKey: String): String =
        RoomFutureImpl(id, phone).sendStateEvent(content, stateKey)
}