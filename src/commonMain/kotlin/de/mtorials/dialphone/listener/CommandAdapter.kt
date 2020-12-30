package de.mtorials.dialphone.listener

import de.mtorials.dialphone.dialevents.MessageReceivedEvent

abstract class CommandAdapter(
    val invoke: String
) : ListenerAdapter() {
    override suspend fun onRoomMessageReceive(event: MessageReceivedEvent) {
        if (event.phone.profile.receiveID() == event.sender.id) return
        if (!event.message.body.startsWith(event.phone.commandPrefix + invoke)) return
        val parameters = event.message.body.split(" ").toMutableList()
        parameters.removeAt(0)
        execute(event, parameters.toTypedArray())
    }

    abstract suspend fun execute(event: MessageReceivedEvent, parameters: Array<String>)
}