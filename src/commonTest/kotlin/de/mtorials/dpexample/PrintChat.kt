package de.mtorials.dpexample

import de.mtorials.dialphone.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.listener.ListenerAdapter

class PrintChat(
    private val roomId: String,
    private val printSelf: Boolean = false
) : ListenerAdapter(true) {
    override suspend fun onRoomMessageReceive(event: MessageReceivedEvent) {
        if (event.roomFuture.id != roomId) return
        if (!printSelf && event.sender.id == event.phone.ownId) return
        println("Message by ${event.sender.receive().displayName}: ${event.message.body.trim()}")
    }
}