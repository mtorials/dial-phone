package de.mtorials.dialphone.core.entities.room

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.model.enums.JoinRule
import de.mtorials.dialphone.api.model.enums.Membership
import de.mtorials.dialphone.api.model.mevents.roomstate.*
import de.mtorials.dialphone.core.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.userId
import de.mtorials.dialphone.api.model.mevents.roommessage.MessageEventContent
import de.mtorials.dialphone.core.DialPhoneImpl
import de.mtorials.dialphone.core.entities.Member
import de.mtorials.dialphone.core.entities.MemberImpl
import de.mtorials.dialphone.core.entityfutures.RoomFuture

class JoinedRoomImpl(
    override val phone: DialPhoneImpl,
    override val id: RoomId,
) : JoinedRoom {

    override var name: String
        get() = phone.cache.
    override val members: MutableList<Member> = mutableListOf()
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

    override suspend fun join() : RoomFuture {
        phone.apiRequests.joinRoomWithId(id.toString())
        return RoomFutureImpl(id, phone)
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

    override suspend fun leave() {
        phone.apiRequests.leaveRoomWithId(id.toString())
    }

}