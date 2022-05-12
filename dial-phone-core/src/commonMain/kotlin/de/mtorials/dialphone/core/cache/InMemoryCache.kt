package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.enums.Membership
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomJoinRules
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomName
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import kotlinx.coroutines.joinAll

class InMemoryCache : PhoneCache {
    override val roomCache = object : RoomCache {

        private val states: MutableMap<RoomId, MutableList<MatrixStateEvent>> = mutableMapOf()

        override fun getRoomStateEvents(roomId: RoomId): List<MatrixStateEvent> = states[roomId] ?: emptyList()

        // TODO check if concurrent modification
        override fun insertRoomStateEvent(roomId: RoomId, event: MatrixStateEvent) {
            val state = states[roomId]?.filter {
                // filter out every event that is same type and state key before inserting
                !(event::class.isInstance(it) && event.stateKey == it.stateKey)
            // if null, create mutable list
            }?.toMutableList() ?: mutableListOf()
            state.add(event)
            states[roomId] = state
        }

        override var joinedRoomIds: MutableList<RoomId> = mutableListOf()
        override var invitedRoomIds: MutableList<RoomId> = mutableListOf()
        override var knockedRoomIds: MutableList<RoomId> = mutableListOf()
        override var leftRoomIds: MutableList<RoomId> = mutableListOf()
        override var bannedRoomIds: MutableList<RoomId> = mutableListOf()

        private fun removeRoomIdEverywhere(roomId: RoomId) {
            joinedRoomIds.remove(roomId);
            invitedRoomIds.remove(roomId);
            knockedRoomIds.remove(roomId);
            leftRoomIds.remove(roomId);
        }
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