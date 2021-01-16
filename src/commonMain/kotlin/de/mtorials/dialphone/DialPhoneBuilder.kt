package de.mtorials.dialphone

import de.mtorials.dialphone.entities.EntitySerialization
import de.mtorials.dialphone.listener.Listener
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus
import de.mtorials.dialphone.model.EventSerialization

typealias ListenerList = DialPhoneBuilder.BuilderList<Listener>

class DialPhoneBuilder(
    block: DialPhoneBuilder.() -> Unit
) {
    var homeserverUrl: String? = null
    private var token: String = ""
    private var ownId: String? = null
    private val listenerList = ListenerList()
    private var customSerializer: SerializersModule = SerializersModule {  }
    private var commandPrefix: String? = null
    private var isGuestBool = false

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

    fun addListeners(vararg listeners: Listener) {
       this.listenerList.addAll(listeners as Array<Listener>)
    }

    fun addCustomSerializersModule(serializersModule: SerializersModule) {
        this.customSerializer = serializersModule
    }

    infix fun hasCommandPrefix(commandPrefix: String) {
        this.commandPrefix = commandPrefix
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
        return DialPhoneImpl(
            token = token,
            homeserverUrl = homeserverUrl!!,
            listeners = listenerList.list,
            client = client,
            commandPrefix = commandPrefix ?: "!",
            ownId = id
        )
    }
}