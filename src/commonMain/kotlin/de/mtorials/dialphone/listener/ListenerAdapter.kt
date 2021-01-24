package de.mtorials.dialphone.listener

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.dialevents.RoomInviteEvent
import de.mtorials.dialphone.model.enums.Membership
import de.mtorials.dialphone.model.mevents.MatrixEvent
import de.mtorials.dialphone.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.model.mevents.roomstate.MRoomMember
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class ListenerAdapter(
    private val receivePastEvents: Boolean = false
) : Listener {
    open suspend fun onRoomMessageReceive(event: MessageReceivedEvent) = Unit
    open suspend fun onRoomInvite(event: RoomInviteEvent) = Unit
    //open suspend fun onPresenceChange(event: PresenceChangeEvent) = Unit

    override fun onNewRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone) {
        onEvent(event, roomId, phone)
    }

    override fun onOldRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone) {
        if (receivePastEvents) onEvent(event, roomId, phone)
    }

    private fun onEvent(event: MatrixEvent, roomId: String, phone: DialPhone) {
        when(event) {
            is MRoomMessage -> GlobalScope.launch { onRoomMessageReceive(MessageReceivedEvent(
                roomID = roomId,
                phone = phone,
                event = event
            )) }
            is MRoomMember -> when (event.content.membership) {
                Membership.INVITE -> if (event.stateKey == phone.ownId) GlobalScope.launch {
                    onRoomInvite(RoomInviteEvent(
                        roomId = roomId,
                        phone = phone,
                        event = event
                    ))
                }
            }
        }
    }
}