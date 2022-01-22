package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.listeners.ApiListener
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.UserImpl
import de.mtorials.dialphone.core.ids.RoomId
import de.mtorials.dialphone.core.ids.roomId
import de.mtorials.dialphone.core.ids.userId

class UserCacheListener(
    private val cache: PhoneCache,
    private val phone: DialPhone,
) : ApiListener {
    override fun onRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneApi, isOld: Boolean) {
        cache(event, roomId.roomId())
    }
    private fun cache(event: MatrixEvent, roomId: RoomId) {
        if (event !is MRoomMember) return
        cache.users[event.stateKey] = UserImpl(
            id = event.stateKey.userId(),
            avatarURL = event.content.avatarURL,
            displayName = event.content.displayName,
            phone = phone
        )
    }
}