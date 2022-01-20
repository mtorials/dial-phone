package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.core.entityfutures.UserFuture
import de.mtorials.dialphone.core.ids.RoomId

/**
 * Represents a user in a room
 */
interface Member : UserFuture {
    val roomId: RoomId
}