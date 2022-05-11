package de.mtorials.dialphone.core.cache

import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.ids.roomId
import de.mtorials.dialphone.api.ids.userId
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.DialPhoneImpl
import de.mtorials.dialphone.core.entities.UserImpl

class UserCacheListener(
    private val cache: PhoneCache,
    private val phone: DialPhone,
) : GenericListener<DialPhoneImpl> {

    override fun onRoomEvent(event: MatrixEvent, roomId: RoomId, phone: DialPhoneImpl, isOld: Boolean) {
        cache(event, roomId)
    }

    private fun cache(event: MatrixEvent, roomId: RoomId) {
//        if (event !is MRoomMember) return
//        cache.users[event.stateKey] = UserImpl(
//            id = event.stateKey.userId(),
//            avatarURL = event.content.avatarURL,
//            displayName = event.content.displayName,
//            phone = phone
//        )
    }
}