package de.mtorials.dialphone.core.entities.room

import de.mtorials.dialphone.api.responses.DiscoveredRoomResponse
import de.mtorials.dialphone.core.entities.Entity

interface DiscoveredRoom : Entity {
    val information: DiscoveredRoomResponse
    suspend fun join() : JoinedRoom
}