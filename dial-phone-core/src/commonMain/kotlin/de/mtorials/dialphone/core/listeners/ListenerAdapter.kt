package de.mtorials.dialphone.core.listeners

import de.mtorials.dialphone.api.listeners.Listener
import de.mtorials.dialphone.core.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.core.dialevents.RoomInviteEvent

interface ListenerAdapter : Listener {
    suspend fun onRoomMessageReceived(block: suspend (MessageReceivedEvent) -> Unit)
    suspend fun onRoomInvited(block: suspend (RoomInviteEvent) -> Unit)

    companion object {
        operator fun invoke(receivePastEvents: Boolean = false) = ListenerAdapterImpl(receivePastEvents)
    }
}