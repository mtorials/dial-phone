package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.DialPhoneApiBuilderImpl
import de.mtorials.dialphone.core.cache.InMemoryCache
import de.mtorials.dialphone.core.cache.PhoneCache
import de.mtorials.dialphone.core.cache.RoomCacheListener
import de.mtorials.dialphone.core.cache.UserCacheListener

class DialPhoneBuilderImpl(
    homeserverUrl: String,
) : DialPhoneBuilder, DialPhoneApiBuilderImpl(
    homeserverUrl = homeserverUrl
) {
    override var cache: PhoneCache? = null
    private var afterInitialization: DialPhoneBuilderImpl.(DialPhoneImpl) -> Unit = {}

    override fun afterInitialization(block: DialPhoneBuilderImpl.(DialPhoneImpl) -> Unit) {
       afterInitialization = block
    }

    override fun cacheInMemory() {
        cache = InMemoryCache()
    }

    suspend fun buildDialPhone(block: DialPhoneBuilderImpl.() -> Unit) : DialPhoneImpl {
        block()
        this.configure()
        val c = cache ?: InMemoryCache()
        return DialPhoneImpl(
            token = this.token!!,
            homeserverUrl = homeserverUrl,
            client = client,
            // TODO use better errors
            ownId = ownId ?: error("Got no di"),
            deviceId = deviceId,
            initCallback = {},
            coroutineScope = coroutineScope,
            cache = c,
            format = format,
            logLevel = dialPhoneLogLevel,
        ).also {
            it.addListeners(*listenerList.toTypedArray())
            it.addListeners(UserCacheListener(
                c,
                it,
            ))
            it.addListeners(RoomCacheListener(
                c.roomCache
            ))
            afterInitialization(it)
        }
    }
}