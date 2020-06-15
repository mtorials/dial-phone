package de.mtorials.dial.listener

import de.mtorials.dial.events.MessageReceivedEvent
import de.mtorials.dial.events.PresenceChangeEvent

interface Listener {
    suspend fun onRoomMessageReceive(event: MessageReceivedEvent)
    suspend fun onPrivateMessageReceive(event: MessageReceivedEvent)
    suspend fun onPresenceChange(event: PresenceChangeEvent)
}