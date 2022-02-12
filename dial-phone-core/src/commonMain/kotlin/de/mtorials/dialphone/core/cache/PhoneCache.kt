package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.core.entities.User


interface PhoneCache {
    val roomCache: RoomCache
    //val messageCache: MessageCache
    //val userCache: UserCache
}