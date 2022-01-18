package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.authentication.Login
import de.mtorials.dialphone.api.authentication.Registrar
import de.mtorials.dialphone.api.listeners.Command
import de.mtorials.dialphone.api.listeners.CommandListener
import de.mtorials.dialphone.api.listeners.Listener
import de.mtorials.dialphone.api.model.mevents.EventSerialization
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus

/**
 * Internal class that is open for other modules to build upon
 */
open class DialPhoneApiBuilderImpl<R: DialPhoneApi>(
    block: DialPhoneApiBuilder<R>.() -> Unit,
    var homeserverUrl: String,
) : DialPhoneApiBuilder<R> {
    // TODO make all lateinit
    private var token: String? = null
    private var ownId: String? = null
    private var username: String? = null
    private var password: String? = null
    private var createUserIfNoRegistered: Boolean = false
    private val listenerList: MutableList<Listener> = mutableListOf()
    private var customSerializer: SerializersModule = SerializersModule {  }
    private var isGuestBool = false
    private var commandListener: CommandListener? = null
    private var bot: DialPhoneApiBuilder.BotBuilder? = null

    lateinit var format: Json
    lateinit var client: HttpClient

    init {
        this.block()
    }

    override fun asGuest() {
        isGuestBool = true
    }

    override fun asUser(username: String, password: String, createUserIfNotRegistered : Boolean) {
        this.password = password
        this.username = username
        this.createUserIfNoRegistered = createUserIfNoRegistered
    }

    override infix fun withToken(token: String) {
        this.token = token
    }

    override fun addListeners(vararg listeners: Listener) {
       this.listenerList.addAll(listeners as Array<Listener>)
    }

    override fun addCustomSerializersModule(serializersModule: SerializersModule) {
        this.customSerializer = serializersModule
    }

    override fun bot(block: DialPhoneApiBuilder.BotBuilder.() -> Unit) {
        val botBuilder = BotBuilderImpl()
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

    protected suspend fun configure() {
        format = Json {
            ignoreUnknownKeys = true
            classDiscriminator = "type"
            encodeDefaults = true
            serializersModule =
                    // TODO check if I broke smth
                EventSerialization.serializersModule + customSerializer
        }
        client = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(format)
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
        if (isGuestBool) {
            val guest = Registrar(client).registerGuest(homeserverUrl)
            token = guest.token
            ownId = guest.userId
        }
        if (token == null) {
            if (username == null || password == null) error("choose login method in builder")
            try {
                val response = Login(homeserverUrl = homeserverUrl, httpClient = client).login(
                    username = username!!,
                    password = password!!
                )
                token = response.accessToken
            } catch (e: ClientRequestException) {
                if (e.response.status.value == 403) {
                    if (createUserIfNoRegistered) {
                        try {
                            val reqRes = Registrar(client).registerUser(
                                homeserverUrl,
                                username = username!!,
                                password = password!!
                            )
                            token = reqRes.accessToken
                        } catch (e2: ClientRequestException) {
                            // TODO Use better exception type
                            throw RuntimeException("Could not register user", e2)
                        }
                    } else error("User Does Not Exist!")
                } else {
                    // TODO use better exception type
                    throw RuntimeException("Could not login", e)
                }
            }

        }
        if (commandListener != null) listenerList.add(commandListener!!)
    }

    // TODO why do i have to cast?
    suspend fun build() : R {
        configure()
        val id = APIRequests(homeserverUrl = homeserverUrl!!, token = token!!, client = client).getMe().id
        return DialPhoneApiImpl(
            token = token!!,
            homeserverUrl = homeserverUrl!!,
            listeners = listenerList,
            client = client,
            ownId = id,
            initCallback = {}
        ) as R
    }

    class BotBuilderImpl : DialPhoneApiBuilder.BotBuilder {

        override val bot = this

        override var commandPrefix: String = "!"
        val regCommands: MutableList<Command> = mutableListOf()
        var help: Boolean = false
            private set

        override fun generateHelp() {
            this.help = true
        }

        override fun commands(vararg commands: Command) {
            regCommands.addAll(commands)
        }
    }
}