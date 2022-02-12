package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember

interface UserCache {
    fun getMemberEventForUserId(userId: UserId): MRoomMember?
    fun cacheMemberEvent(userId: UserId, event: MRoomMember)
}