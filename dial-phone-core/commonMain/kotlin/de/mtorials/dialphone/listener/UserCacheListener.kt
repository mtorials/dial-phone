package de.mtorials.dialphone.listener

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.PhoneCache
import de.mtorials.dialphone.entities.UserImpl
import de.mtorials.dialphone.model.mevents.MatrixEvent
import de.mtorials.dialphone.model.mevents.roomstate.MRoomMember

class UserCacheListener(private val cache: PhoneCache) : Listener {
    override fun onNewRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone) {
        cache(event, roomId, phone)
    }

    override fun onOldRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone) {
        cache(event, roomId, phone)
    }

    private fun cache(event: MatrixEvent, roomId: String, phone: DialPhone) {
        if (event !is MRoomMember) return
        cache.users[event.stateKey] = UserImpl(
            id = event.stateKey,
            avatarURL = event.content.avatarURL,
            displayName = event.content.displayName,
            phone = phone
        )
    }
}