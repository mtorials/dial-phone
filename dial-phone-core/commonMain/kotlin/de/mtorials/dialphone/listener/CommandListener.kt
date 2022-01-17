package de.mtorials.dialphone.listener

import de.mtorials.dialphone.dialevents.MessageReceivedEvent

class CommandListener internal constructor(
    private val prefix: String,
    private val commands: List<Command>,
    private val fallbackCommand: Command?
): ListenerAdapter() {
    override suspend fun onRoomMessageReceive(event: MessageReceivedEvent) {
        println(fallbackCommand)
        if (!event.message.body.startsWith(prefix)) return
        var found = false
        val parameters = event.message.body.split(" ").toMutableList()
        commands.forEach loop@{ command ->
            if (!event.message.body.startsWith(prefix + command.invoke)) return@loop
            parameters.removeAt(0)
            command.execute(event, parameters.toTypedArray())
            found = true
        }
        if (!found && fallbackCommand != null) {
            fallbackCommand.execute(event, arrayOf())
        }
    }
}