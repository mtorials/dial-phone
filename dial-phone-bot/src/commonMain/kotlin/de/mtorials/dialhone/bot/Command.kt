package de.mtorials.dialhone.bot

import de.mtorials.dialphone.core.dialevents.MessageReceivedEvent

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
        ): Command {
            return object : Command(invoke, description, usage) {
                override suspend fun execute(event: MessageReceivedEvent, parameters: Array<String>) {
                    executeBlock(event, parameters)
                }
            }
        }
    }
}