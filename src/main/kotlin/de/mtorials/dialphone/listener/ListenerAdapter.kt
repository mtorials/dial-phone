package de.mtorials.dialphone.listener

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.events.MessageReceivedEvent
import de.mtorials.dialphone.events.PresenceChangeEvent
import de.mtorials.dialphone.mevents.MatrixEvent
import de.mtorials.dialphone.mevents.room.MRoomMessage
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