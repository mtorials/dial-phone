package de.mtorials.example

import de.mtorials.dialphone.events.MessageReceivedEvent
import de.mtorials.dialphone.events.answer
import de.mtorials.dialphone.listener.ListenerAdapter
import de.mtorials.example.cutstomevents.PositionEvent

class ExampleListener: ListenerAdapter() {
    override suspend fun onRoomMessageReceive(event: MessageReceivedEvent) {
        // Don't answer to your own messages
        if (event.senderId == event.phone.ownId) return
        println("user ${event.senderId} send ${event.content.body}")
        event answer "Hi"
        event.roomFuture.sendMessageEvent(PositionEvent.Content(1, 3, 5))
    }
}