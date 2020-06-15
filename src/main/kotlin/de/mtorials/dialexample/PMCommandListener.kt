package de.mtorials.dialexample

import de.mtorials.dial.events.MessageReceivedEvent
import de.mtorials.dial.events.answer
import de.mtorials.dial.listener.CommandAdapter

class PMCommandListener: CommandAdapter("dpm") {
    override suspend fun execute(event: MessageReceivedEvent, parameters: Array<String>) {
        event answer "This is an event!"
    }
}