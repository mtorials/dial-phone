package de.mtorials.dialphone.core.listeners

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.api.listeners.Listener
import de.mtorials.dialphone.api.model.enums.Membership
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.api.model.mevents.roomstate.MRoomMember
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.core.dialevents.RoomInviteEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Make interface
abstract class ListenerAdapter(
    private val receivePastEvents: Boolean = false
) : Listener {
    open suspend fun onRoomMessageReceive(event: MessageReceivedEvent) = Unit
    open suspend fun onRoomInvite(event: RoomInviteEvent) = Unit
    //open suspend fun onPresenceChange(event: PresenceChangeEvent) = Unit

    override fun onNewRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneApi) {
        onEvent(event, roomId, phone)
    }

    override fun onOldRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhoneApi) {
        if (receivePastEvents) onEvent(event, roomId, phone)
    }

    private fun onEvent(event: MatrixEvent, roomId: String, phone: DialPhone) {
        when(event) {
            is MRoomMessage -> GlobalScope.launch { onRoomMessageReceive(
                MessageReceivedEvent(
                roomID = roomId,
                phone = phone,
                event = event
            )
            ) }
            is MRoomMember -> when (event.content.membership) {
                Membership.INVITE -> if (event.stateKey == phone.ownId) GlobalScope.launch {
                    onRoomInvite(
                        RoomInviteEvent(
                        roomId = roomId,
                        phone = phone,
                        event = event
                    )
                    )
                }
            }
        }
    }
}