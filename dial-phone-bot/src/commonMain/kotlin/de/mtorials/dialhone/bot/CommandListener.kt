package de.mtorials.dialhone.bot

import de.mtorials.dialphone.core.EventCallback
import de.mtorials.dialphone.core.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.core.listeners.ListenerAdapterImpl

class CommandListener constructor(
    private val prefix: String,
    private val commands: List<Command>,
    private val fallbackCommand: Command?
) : ListenerAdapterImpl(false){
    override var callbackOnMessageReceived: EventCallback<MessageReceivedEvent> = call@{ event ->
        println(fallbackCommand)
        if (!event.message.body.startsWith(prefix)) return@call
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