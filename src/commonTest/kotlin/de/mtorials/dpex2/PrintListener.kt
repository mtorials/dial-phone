package de.mtorials.dpex2

import de.mtorials.dialphone.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.listener.ListenerAdapter

class PrintListener : ListenerAdapter(receivePastEvents = false) {
    override suspend fun onRoomMessageReceive(event: MessageReceivedEvent) {
        println(event.message.body)
    }
}