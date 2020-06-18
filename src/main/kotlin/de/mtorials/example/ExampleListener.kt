package de.mtorials.example

import de.mtorials.dialphone.events.MessageReceivedEvent
import de.mtorials.dialphone.events.answer
import de.mtorials.dialphone.listener.ListenerAdapter
import de.mtorials.dialphone.rename
import de.mtorials.example.cutstomevents.PositionEvent

class ExampleListener: ListenerAdapter() {
    override suspend fun onRoomMessageReceive(event: MessageReceivedEvent) {
        if (event.senderId == event.phone.ownId) return
        event.roomFuture rename "[Demo] ${event.content.body}"
    }
}