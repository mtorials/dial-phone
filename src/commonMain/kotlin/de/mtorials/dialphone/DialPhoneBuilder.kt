package de.mtorials.dialphone

import de.mtorials.dialphone.listener.Listener
import net.mt32.makocommons.mevents.MatrixEvent
import kotlin.reflect.KClass

typealias CustomEventList = DialPhoneBuilder.BuilderList<KClass<out MatrixEvent>>
typealias ListenerList = DialPhoneBuilder.BuilderList<Listener>

class DialPhoneBuilder(
    block: DialPhoneBuilder.() -> Unit
) {
    var homeserverUrl: String? = null
    private var token: String = ""
    private var ownId: String? = null
    private val listenerList = ListenerList()
    private val customEventList = CustomEventList()
    private var commandPrefix: String? = null

    val client = this

    init {
        this.block()
    }

    suspend fun isGuest() {
        val guest = Registrar.registerGuest(homeserverUrl ?: throwNoHomeserver())
        token = guest.token
        ownId = guest.userId
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

    infix fun addCustomEventTypes(block: CustomEventList.() -> CustomEventList) {
        customEventList.block()
    }

    fun addCustomEventTypes(vararg types: KClass<out MatrixEvent>) {
        customEventList.addAll(types as Array<KClass<out MatrixEvent>>)
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
        if (homeserverUrl == null) throwNoHomeserver()
        val id = APIRequests(homeserverUrl = homeserverUrl!!, token = token).getMe().id
        return DialPhoneImpl(
            token = token,
            homeserverUrl = homeserverUrl!!,
            listeners = listenerList.list,
            customEventTypes = customEventList.list.toTypedArray(),
            commandPrefix = commandPrefix ?: "!",
            ownId = id
        )
    }
}