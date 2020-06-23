package de.mtorials.dpexample

import de.mtorials.dialphone.events.MessageReceivedEvent
import de.mtorials.dialphone.listener.ListenerAdapter
import de.mtorials.dpexample.cutstomevents.TestStateEvent

class ExampleListener: ListenerAdapter() {
    override suspend fun onRoomMessageReceive(event: MessageReceivedEvent) {
        if (event.senderId == event.phone.ownId) return
        //event.roomFuture rename "[Demo] ${event.content.body}"
        event.roomFuture.sendStateEvent(TestStateEvent.Content("I AM a state!"))
    }
}