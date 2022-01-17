package de.mtorials.dialphone.core.listener

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.PhoneCache
import de.mtorials.dialphone.core.model.mevents.MatrixEvent
import de.mtorials.dialphone.core.model.mevents.roomstate.MRoomMember

class UserCacheListener(private val cache: PhoneCache) : Listener {
    override fun onNewRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone) {
        cache(event, roomId, phone)
    }

    override fun onOldRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone) {
        cache(event, roomId, phone)
    }

    private fun cache(event: MatrixEvent, roomId: String, phone: DialPhone) {
        if (event !is MRoomMember) return
        cache.users[event.stateKey] = de.mtorials.dialphone.core.entities.UserImpl(
            id = event.stateKey,
            avatarURL = event.content.avatarURL,
            displayName = event.content.displayName,
            phone = phone
        )
    }
}