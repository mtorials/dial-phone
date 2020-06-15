package de.mtorials.dial.listener

import de.mtorials.dial.events.MessageReceivedEvent
import de.mtorials.dial.events.PresenceChangeEvent

abstract class ListenerAdapter : Listener {
    override suspend fun onRoomMessageReceive(event: MessageReceivedEvent) = Unit
    override suspend fun onPrivateMessageReceive(event: MessageReceivedEvent) = Unit
    override suspend fun onPresenceChange(event: PresenceChangeEvent) = Unit
}