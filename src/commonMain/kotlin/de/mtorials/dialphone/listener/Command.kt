package de.mtorials.dialphone.listener

import de.mtorials.dialphone.dialevents.MessageReceivedEvent

abstract class Command(
    val invoke: String,
    val description: String? = null,
    val usage: String? = null
) {
    abstract suspend fun execute(event: MessageReceivedEvent, parameters: Array<String>)

    companion object {
        operator fun invoke(
            invoke: String,
            description: String? = null,
            usage: String? = null,
            executeBlock: suspend MessageReceivedEvent.(Array<String>)->Unit
        ): DefaultCommand {
            return DefaultCommand(invoke, description, executeBlock)
        }
    }

    class DefaultCommand(
        invoke: String,
        description: String? = null,
        val executeBlock: suspend (MessageReceivedEvent, Array<String>)->Unit
    ) : Command(invoke, description) {
        override suspend fun execute(event: MessageReceivedEvent, parameters: Array<String>) {
            executeBlock(event, parameters)
        }
    }
}