package de.mtorials.dialphone

import de.mtorials.dialphone.listener.Listener
import de.mtorials.dialphone.mevents.MatrixEvent
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KClass

typealias CustomEventList = DialPhoneBuilder.BuilderList<KClass<out MatrixEvent>>
typealias ListenerList = DialPhoneBuilder.BuilderList<Listener>

class DialPhoneBuilder(
    block: DialPhoneBuilder.() -> Unit
) {
    var homeserverUrl: String = ""
    private var token: String = ""
    private var ownId: String? = null
    private val listenerList = ListenerList()
    private val customEventList = CustomEventList()

    init {
        this.block()
    }

    fun asGuest() {
        val guest = runBlocking { Registrator.registerGuest(homeserverUrl) }
        token = guest.token
        ownId = guest.userId
    }

    fun withToken(token: String) {
        this.token = token
    }

    infix fun addListeners(block: ListenerList.() -> ListenerList) {
        listenerList.block()
    }

    infix fun addCustomEventTypes(block: CustomEventList.() -> CustomEventList) {
        customEventList.block()
    }

    class BuilderList<T> {
        val list: MutableList<T> = mutableListOf()
        fun add(el: T): BuilderList<T> {
            list.add(el)
            return this
        }
    }

    fun build() : DialPhone = DialPhoneImpl(
        token = token,
        userId = ownId,
        homeserverUrl = homeserverUrl,
        listeners = listenerList.list,
        customEventTypes = customEventList.list.toTypedArray()
    )
}