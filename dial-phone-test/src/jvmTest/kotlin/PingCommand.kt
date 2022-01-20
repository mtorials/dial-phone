import de.mtorials.dialphone.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.dialevents.answer
import de.mtorials.dialphone.listener.Command

class HelpCommand: Command("help") {
    override suspend fun execute(event: MessageReceivedEvent, parameters: Array<String>) {
        event answer "This is a help command"
    }
}