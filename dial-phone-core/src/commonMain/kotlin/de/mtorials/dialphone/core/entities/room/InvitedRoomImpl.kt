package de.mtorials.dialphone.core.entities.room

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomName
import de.mtorials.dialphone.core.DialPhoneImpl

class InvitedRoomImpl internal constructor(
    override val phone: DialPhoneImpl,
    override val id: RoomId,
) : InvitedRoom {

    override val name: String?
        get() = phone.cache.state.getRoomStateEvents(id).filterIsInstance<MRoomName>().getOrNull(0)?.content?.name


    override suspend fun join() : JoinedRoom {
        phone.apiRequests.joinRoomWithId(id)
        return JoinedRoomImpl(
            phone = phone,
            id = id,
        )
    }

}