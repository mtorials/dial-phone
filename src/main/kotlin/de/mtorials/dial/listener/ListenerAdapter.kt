package de.mtorials.dial.listener

import de.mtorials.dial.DialPhone
import de.mtorials.dial.events.MessageReceivedEvent
import de.mtorials.dial.events.PresenceChangeEvent
import de.mtorials.dial.mevents.MatrixEvent
import de.mtorials.dial.mevents.room.MRoomMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class ListenerAdapter : Listener {
    open suspend fun onRoomMessageReceive(event: MessageReceivedEvent) = Unit
    open suspend fun onPrivateMessageReceive(event: MessageReceivedEvent) = Unit
    open suspend fun onPresenceChange(event: PresenceChangeEvent) = Unit

    override fun onRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone) {
        when(event) {
            is MRoomMessage -> GlobalScope.launch { onRoomMessageReceive(MessageReceivedEvent(
                roomID = roomId,
                phone = phone,
                event = event
            )) }
        }
    }
}