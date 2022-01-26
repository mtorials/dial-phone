package de.mtorials.dialphone.core.listeners

import de.mtorials.dialphone.core.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.core.dialevents.RoomInviteEvent

interface ListenerAdapter {
    fun onRoomMessageReceived(block: suspend (MessageReceivedEvent) -> Unit)
    fun onRoomInvited(block: suspend (RoomInviteEvent) -> Unit)

    companion object {
        operator fun invoke(receivePastEvents: Boolean = false, block: ListenerAdapter.() -> Unit) : ListenerAdapterImpl {
            val adapter = ListenerAdapterImpl(receivePastEvents)
            adapter.block()
            return adapter
        }
    }
}