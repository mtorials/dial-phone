package de.mtorials.dialphone.listener

import de.mtorials.dialphone.dialevents.MessageReceivedEvent

abstract class CommandAdapter(
    val invoke: String
) : ListenerAdapter() {
    override suspend fun onRoomMessageReceive(event: MessageReceivedEvent) {
        if (event.phone.ownId == event.senderId) return
        if (!event.content.body.startsWith(event.phone.commandPrefix + invoke)) return
        val parameters = event.content.body.split(" ").toMutableList()
        parameters.removeAt(0)
        execute(event, parameters.toTypedArray())
    }

    abstract suspend fun execute(event: MessageReceivedEvent, parameters: Array<String>)
}