package de.mtorials.dialphone.entities

import de.mtorials.dialphone.entities.entityfutures.RoomFuture
import de.mtorials.dialphone.entities.entityfutures.UserFuture
import de.mtorials.dialphone.enums.JoinRule
import de.mtorials.dialphone.enums.Membership
import de.mtorials.dialphone.mevents.EventContent
import de.mtorials.dialphone.mevents.room.MRoomJoinRules
import de.mtorials.dialphone.mevents.room.MRoomMember
import de.mtorials.dialphone.mevents.room.MRoomName
import de.mtorials.dialphone.mevents.MatrixEvent
import de.mtorials.dialphone.mevents.room.MRoomAvatar
import kotlin.reflect.KClass

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

    override suspend fun sendEvent(content: EventContent): String =
        RoomFuture(id, phone).sendEvent(content)
}