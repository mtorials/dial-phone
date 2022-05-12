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

    override val name: String?
        get() = getStateEvent<MRoomName>()?.content?.name

    override val members: List<Member>
        get() = phone.cache.state.getRoomStateEvents(roomId = id).filterIsInstance<MRoomMember>()
            .filter { it.content.membership == Membership.JOIN }
            .map { event ->
                MemberImpl(
                    user = UserImpl(
                        phone = phone,
                        id = event.stateKey.userId(),
                    ),
                    room = this,
                )
            }

    override val avatarUrl: String?
        get() = getStateEvent<MRoomAvatar>()?.content?.url

    override val joinRule : JoinRule?
        get() = getStateEvent<MRoomJoinRules>()?.content?.joinRule

    override val stateEvents: List<MatrixStateEvent>
        get() = phone.cache.state.getRoomStateEvents(roomId = id)

    private inline fun <reified T : MatrixStateEvent> getStateEvent() : T? {
        return phone.cache.state.getRoomStateEvents(roomId = id).filterIsInstance<T>().getOrNull(0)
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

}