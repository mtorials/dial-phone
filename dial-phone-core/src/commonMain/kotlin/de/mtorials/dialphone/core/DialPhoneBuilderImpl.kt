package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.APIRequests
import de.mtorials.dialphone.api.DialPhoneApiBuilderImpl
import de.mtorials.dialphone.api.EventHook
import de.mtorials.dialphone.core.cache.InMemoryCache
import de.mtorials.dialphone.core.cache.PhoneCache
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
        return DialPhoneImpl(
            token = this.token!!,
            homeserverUrl = homeserverUrl,
            client = client,
            // TODO use better errors
            ownId = ownId ?: error("Got no di"),
            deviceId = deviceId,
            initCallback = {},
            coroutineScope = coroutineScope,
            cache = cache,
        ).also {
            it.addListeners(*listenerList.toTypedArray())
            if (cache != null) this.addListeners(UserCacheListener(
                cache!!,
                it,
            ))
            afterInitialization(it)
        }
    }
}