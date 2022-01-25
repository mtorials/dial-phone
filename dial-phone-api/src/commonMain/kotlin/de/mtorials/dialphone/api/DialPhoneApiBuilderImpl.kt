package de.mtorials.dialphone.api

import de.mtorials.dialphone.api.authentication.Login
import de.mtorials.dialphone.api.authentication.Registrar
import de.mtorials.dialphone.api.listeners.ApiListener
import de.mtorials.dialphone.api.listeners.GenericListener
import de.mtorials.dialphone.api.model.mevents.EventContentSerialization
import de.mtorials.dialphone.api.model.mevents.EventSerialization
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus

/**
 * Internal class that is open for other modules to build upon
 */
open class DialPhoneApiBuilderImpl(
    val homeserverUrl: String,
) : DialPhoneApiBuilder {

    override var ktorLogLevel = LogLevel.NONE

    protected var token: String? = null
    private var ownId: String? = null
    private var username: String? = null
    private var password: String? = null
    private var createUserIfNoRegistered: Boolean = false
    protected val listenerList: MutableList<GenericListener<*>> = mutableListOf()
    protected var customSerializer: SerializersModule = SerializersModule {  }
    protected var coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    private var isGuestBool = false
    // TODO remove bot stuff
    // protected var commandListener: CommandListener? = null
    // private var bot: DialPhoneApiBuilder.BotBuilder? = null

    lateinit var format: Json
    lateinit var client: HttpClient

    override fun asGuest() {
        isGuestBool = true
    }

    override fun asUser(username: String, password: String, createUserIfNotRegistered : Boolean) {
        this.password = password
        this.username = username
        this.createUserIfNoRegistered = createUserIfNotRegistered
    }

    override infix fun withToken(token: String) {
        this.token = token
    }

    override fun addListeners(vararg listeners: GenericListener<*>) {
       this.listenerList.addAll(listeners)
    }

    override fun addCustomSerializersModule(serializersModule: SerializersModule) {
        this.customSerializer = serializersModule
    }

    override fun withCoroutineDispatcher(coroutineScope: CoroutineScope) {
        this.coroutineScope = coroutineScope
    }

//    override fun bot(block: DialPhoneApiBuilder.BotBuilder.() -> Unit) {
//        val botBuilder = BotBuilderImpl()
//        botBuilder.block()
//        var fall: Command? = null
//        if (botBuilder.help) fall = Command("help", "Gives Help", "help <command>") {
//            this answer "Help:"
//            botBuilder.regCommands.forEach { command ->
//                this answer command.invoke + ": " + command.usage
//            }
//        }
//        if (fall != null) botBuilder.regCommands.add(fall)
//        this.commandListener = CommandListener(prefix = botBuilder.commandPrefix, commands = botBuilder.regCommands, fallbackCommand = fall)
//        this.bot = botBuilder
//    }

    @OptIn(ExperimentalSerializationApi::class)
    protected suspend fun configure() {
        format = Json {
            ignoreUnknownKeys = true
            classDiscriminator = "type"
            encodeDefaults = true
            explicitNulls = false
            serializersModule =
                    // TODO check if I broke smth
                EventSerialization.serializersModule + EventContentSerialization.serializersModule + customSerializer
        }
        client = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(format)
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = ktorLogLevel
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
//        if (commandListener != null) listenerList.add(commandListener!!)
    }

    open suspend fun buildDialPhoneApi(block: DialPhoneApiBuilder.() -> Unit) : DialPhoneApiImpl {
        block()
        configure()
        // TODO necessary here?
        val apiRequests = APIRequests(homeserverUrl = homeserverUrl, token = token!!, client = client)
        val me = apiRequests.getMe()
        return DialPhoneApiImpl(
            token = token!!,
            homeserverUrl = homeserverUrl,
            client = client,
            ownId = apiRequests.getMe().id,
            initCallback = {},
            coroutineScope = coroutineScope,
            deviceId = me.deviceId
        ).also { it.addListeners(*listenerList.toTypedArray()) }
    }

//    class BotBuilderImpl : DialPhoneApiBuilder.BotBuilder {
//
//        override val bot = this
//
//        override var commandPrefix: String = "!"
//        val regCommands: MutableList<Command> = mutableListOf()
//        var help: Boolean = false
//            private set
//
//        override fun generateHelp() {
//            this.help = true
//        }
//
//        override fun commands(vararg commands: Command) {
//            regCommands.addAll(commands)
//        }
//    }
}