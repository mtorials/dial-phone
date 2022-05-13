package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.DialPhoneApiBuilderImpl
import de.mtorials.dialphone.core.cache.*

class DialPhoneBuilderImpl(
    homeserverUrl: String,
) : DialPhoneBuilder, DialPhoneApiBuilderImpl(
    homeserverUrl = homeserverUrl
) {
    override var stateCache: StateCache? = null
    override var timelineCache: TimelineCache? = null
    private var afterInitialization: DialPhoneBuilderImpl.(DialPhoneImpl) -> Unit = {}

    override fun afterInitialization(block: DialPhoneBuilderImpl.(DialPhoneImpl) -> Unit) {
       afterInitialization = block
    }

    suspend fun buildDialPhone(block: DialPhoneBuilderImpl.() -> Unit) : DialPhoneImpl {
        block()
        this.configure()
        val c = object : PhoneCache {
            override val state: StateCache = stateCache ?: InMemoryStateCache()
            override val timeline: TimelineCache? = timelineCache
        }
        return DialPhoneImpl(
            token = this.token!!,
            homeserverUrl = homeserverUrl,
            client = client,
            ownId = ownId ?: error("Got no id"),
            deviceId = deviceId,
            initCallback = {},
            coroutineScope = coroutineScope,
            cache = c,
            format = format,
            logLevel = dialPhoneLogLevel,
        ).also {
            it.addListeners(*listenerList.toTypedArray())
            it.addListeners(StateCacheListener(
                c.state
            ))
            afterInitialization(it)
        }
    }
}