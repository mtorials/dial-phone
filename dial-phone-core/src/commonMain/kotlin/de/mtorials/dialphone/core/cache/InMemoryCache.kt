package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.Membership
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomJoinRules
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomName
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent

class InMemoryCache : PhoneCache {
    override val roomCache = object : RoomCache {

        private val states: MutableMap<RoomId, MutableList<MatrixStateEvent>> = mutableMapOf()

        override fun getRoomStateEvents(roomId: RoomId): List<MatrixStateEvent> = states[roomId] ?: emptyList()

        // TODO testing!
        override fun insertRoomStateEvent(roomId: RoomId, event: MatrixStateEvent) {
//            val state = states[roomId]?.filter {
//                // filter out every event that is same type and state key before inserting
//                !(event::class.isInstance(it) && event.stateKey == it.stateKey)
//            // if null, create mutable list
//            }?.toMutableList() ?: mutableListOf()
            val state = states[roomId] ?: mutableListOf()
            if (event is MRoomMember && event.content.membership == Membership.JOIN) {
                // Other join types
                if (!joinedRoomIds.contains(roomId)) joinedRoomIds.add(roomId)
            }
            state.add(event)
            states[roomId] = state
        }

        override var joinedRoomIds: MutableList<RoomId> = mutableListOf()
        override var invitedRoomIds: MutableList<RoomId> = mutableListOf()
        override var knockedRoomIds: MutableList<RoomId> = mutableListOf()
        override var leftRoomIds: MutableList<RoomId> = mutableListOf()
    }

    // TODO check
    // Is it allows to have different user data in different rooms?
    override val userCache = object : UserCache {

        val eventsByUserId: MutableMap<UserId, MRoomMember> = mutableMapOf()

        override fun getMemberEventForUserId(userId: UserId): MRoomMember? {
            return eventsByUserId[userId]
        }

        override fun cacheMemberEvent(userId: UserId, event: MRoomMember) {
            eventsByUserId[userId] = event
        }
    }

    override val messageCache: MessageCache? = null
}