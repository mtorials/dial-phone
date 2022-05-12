package de.mtorials.dialphone.core.entities.room

import de.mtorials.dialphone.api.ids.MatrixID
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.responses.DiscoveredRoomResponse
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.DialPhoneImpl

class DiscoveredRoomImpl(
    override val phone: DialPhoneImpl,
    override val id: RoomId,
    override val information: DiscoveredRoomResponse,
) : DiscoveredRoom {

    override suspend fun join(): JoinedRoom {
        phone.apiRequests.joinRoomWithId(id)
        return JoinedRoomImpl(phone, id)
    }
}