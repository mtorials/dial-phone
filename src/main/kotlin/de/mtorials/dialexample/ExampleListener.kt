package de.mtorials.dialexample

import de.mtorials.dial.events.MessageReceivedEvent
import de.mtorials.dial.events.answer
import de.mtorials.dial.listener.ListenerAdapter

class ExampleListener: ListenerAdapter() {
    override suspend fun onRoomMessageReceive(event: MessageReceivedEvent) {
        // Don't answer to your own messages
        if (event.senderId == event.phone.ownId) return
        println("user ${event.senderId} send ${event.content.body}")
        event answer "Hi!"
    }
}