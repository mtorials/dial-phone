package de.mtorials.dialphone

import de.mtorials.dialphone.dialevents.answer
import de.mtorials.dialphone.entities.EntitySerialization
import de.mtorials.dialphone.listener.Command
import de.mtorials.dialphone.listener.CommandListener
import de.mtorials.dialphone.listener.Listener
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import de.mtorials.dialphone.model.mevents.EventSerialization

typealias ListenerList = DialPhoneBuilder.BuilderList<Listener>

class DialPhoneBuilder(
    block: DialPhoneBuilder.() -> Unit
) {
    var homeserverUrl: String? = null
    private var token: String = ""
    private var ownId: String? = null
    private val listenerList = ListenerList()
    private var customSerializer: SerializersModule = SerializersModule {  }
    private var isGuestBool = false
    private var commandListener: CommandListener? = null
    private var bot: BotBuilder? = null
    private var initCallback : suspend (DialPhone) -> Unit = {}

    init {
        this.block()
    }

    fun isGuest() {
        isGuestBool = true
    }

    infix fun withToken(token: String) {
        this.token = token
    }

    infix fun addListeners(block: ListenerList.() -> ListenerList) {
        listenerList.block()
    }

    fun afterInitialSync(callback: suspend (DialPhone) -> Unit) {
        this.initCallback = callback
    }

    fun addListeners(vararg listeners: Listener) {
       this.listenerList.addAll(listeners as Array<Listener>)
    }

    fun addCustomSerializersModule(serializersModule: SerializersModule) {
        this.customSerializer = serializersModule
    }

    class BuilderList<T> {
        val list: MutableList<T> = mutableListOf()
        fun add(el: T): BuilderList<T> {
            list.add(el)
            return this
        }
        fun addAll(listeners: Array<T>): BuilderList<T> {
            list.addAll(listeners)
            return this
        }
    }

    private fun throwNoHomeserver() : Nothing {
        throw RuntimeException("No homeserver specified. Please assign a value to homeserverUrl first.")
    }

    fun bot(block: BotBuilder.() -> Unit) {
        val botBuilder = BotBuilder()
        botBuilder.block()
        var fall: Command? = null
        if (botBuilder.help) fall = Command("help", "Gives Help", "help <command>") {
            this answer "Help:"
            botBuilder.regCommands.forEach { command ->
                this answer command.invoke + ": " + command.usage
            }
        }
        if (fall != null) botBuilder.regCommands.add(fall)
        this.commandListener = CommandListener(prefix = botBuilder.commandPrefix, commands = botBuilder.regCommands, fallbackCommand = fall)
        this.bot = botBuilder
    }

    suspend fun build() : DialPhone {
        val format = Json {
            ignoreUnknownKeys = true
            classDiscriminator = "type"
            encodeDefaults = true
            serializersModule =
                EventSerialization.serializersModule + EntitySerialization.serializersModule + customSerializer
        }
        val client = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(format)
            }
        }
        if (isGuestBool) {
            val guest = Registrar(client).registerGuest(homeserverUrl ?: throwNoHomeserver())
            token = guest.token
            ownId = guest.userId
        }
        if (homeserverUrl == null) throwNoHomeserver()
        val id = APIRequests(homeserverUrl = homeserverUrl!!, token = token, client = client).getMe().id
        val listeners: MutableList<Listener> = listenerList.list
        if (commandListener != null) listeners.add(commandListener!!)
        return DialPhoneImpl(
            token = token,
            homeserverUrl = homeserverUrl!!,
            listeners = listeners,
            client = client,
            ownId = id,
            initCallback = this.initCallback
        )
    }

    class BotBuilder {

        val bot = this

        var commandPrefix: String = "!"
        val regCommands: MutableList<Command> = mutableListOf()
        var help: Boolean = false
            private set

        fun generateHelp() {
            this.help = true
        }

        fun commands(vararg commands: Command) {
            regCommands.addAll(commands)
        }
    }
}