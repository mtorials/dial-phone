package de.mtorials.dpexample

import de.mtorials.dialphone.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.dialevents.RoomInviteEvent
import de.mtorials.dialphone.listener.ListenerAdapter

class ExampleListener: ListenerAdapter() {
    override suspend fun onRoomMessageReceive(event: MessageReceivedEvent) {
        if (event.sender.id == event.phone.ownId) return
        if (event.message.body == "asdf") event.message.redact("You are dumb!")
    }

    override suspend fun onRoomInvite(event: RoomInviteEvent) {
        println("Im invited!")
        event.invitedRoomActions.join()
    }
}