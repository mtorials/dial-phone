package de.mtorials.dialphone.core.entities.room

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.core.DialPhoneImpl

class InvitedRoomImpl internal constructor(
    override val phone: DialPhoneImpl,
    override val id: RoomId,
    name: String? = null,
) : InvitedRoom {

    override var name: String? = null

    init {
        if (name != null) this.name = name
        else {
            // TODO!
        }
    }

    override suspend fun join() : JoinedRoom {
        phone.apiRequests.joinRoomWithId(id)
        // TODO use better error
        return phone.getJoinedRoomById(id) ?: error("Can not find joined room.")
    }

    override suspend fun forceUpdate() {
        TODO("Not yet implemented")
    }
}