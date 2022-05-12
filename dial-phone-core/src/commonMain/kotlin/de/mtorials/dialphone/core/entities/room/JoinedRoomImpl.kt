package de.mtorials.dialphone.core.entities.room

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.ids.userId
import de.mtorials.dialphone.api.model.enums.JoinRule
import de.mtorials.dialphone.api.model.enums.Membership
import de.mtorials.dialphone.api.model.mevents.roommessage.MessageEventContent
import de.mtorials.dialphone.api.model.mevents.roomstate.*
import de.mtorials.dialphone.core.DialPhoneImpl
import de.mtorials.dialphone.core.entities.Member
import de.mtorials.dialphone.core.entities.MemberImpl
import de.mtorials.dialphone.core.entities.UserImpl

class JoinedRoomImpl internal constructor(
    override val phone: DialPhoneImpl,
    override val id: RoomId,
) : JoinedRoom {

    override var name: String? = null
    override var members: MutableList<Member> = mutableListOf()
    override var avatarUrl: String? = null
    override var joinRule : JoinRule = JoinRule.INVITE

    init {
        updateProps(stateEvents)
    }

    override val stateEvents: List<MatrixStateEvent>
        get() = phone.cache.roomCache.getRoomStateEvents(roomId = id)

    private fun updateProps(state: List<MatrixStateEvent>) {
        for (event in state) {
            when (event) {
                is MRoomName -> {
//                    println(event.content.name)
                    name = event.content.name
                }
                is MRoomJoinRules -> joinRule = event.content.joinRule
                is MRoomAvatar -> avatarUrl = event.content.url
                // TODO check with ban etc.
                is MRoomMember -> {
                    when (event.content.membership) {
                        Membership.JOIN -> members.add(
                            MemberImpl(
                                user = UserImpl(
                                    phone = phone,
                                    id = event.stateKey.userId(),
                                    avatarURL = event.content.avatarURL,
                                    displayName = event.content.displayName,
                                ),
                                room = this,
                            )
                        )
                        Membership.LEAVE -> members.removeAll { member ->
                            member.id == event.sender
                        }
                    }
                }
            }
        }
    }

    override suspend fun sendMessageEvent(content: MessageEventContent, eventType: String) : EventId {
        return phone.sendMessageEvent(
            type = eventType,
            roomId = id,
            content = content
        )
    }

    override suspend fun sendStateEvent(content: StateEventContent, eventType: String, stateKey: String): EventId {
        return phone.apiRequests.sendStateEvent(eventType, content, id.toString(), stateKey)
    }

    override suspend fun sendStateEvent(content: StateEventContent, eventType: String, stateKey: UserId): EventId {
        return sendStateEvent(content, eventType, stateKey.toString())
    }

    override suspend fun leave() = phone.apiRequests.leaveRoomWithId(id)

    override suspend fun forceUpdate() {
        updateProps(phone.apiRequests.getRoomsState(id))
    }

}