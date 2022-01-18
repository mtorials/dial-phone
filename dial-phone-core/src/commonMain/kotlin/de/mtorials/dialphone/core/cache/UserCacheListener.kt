package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.listeners.Listener
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.UserImpl

class UserCacheListener(
    private val cache: PhoneCache,
    private val phone: DialPhone,
) : Listener {
    override fun onRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneApi, isOld: Boolean) {
        cache(event, roomId)
    }
    private fun cache(event: MatrixEvent, roomId: String) {
        if (event !is MRoomMember) return
        cache.users[event.stateKey] = UserImpl(
            id = event.stateKey,
            avatarURL = event.content.avatarURL,
            displayName = event.content.displayName,
            phone = phone
        )
    }
}