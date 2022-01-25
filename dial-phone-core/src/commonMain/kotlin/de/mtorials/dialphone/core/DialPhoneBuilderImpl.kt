package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.APIRequests
import de.mtorials.dialphone.api.DialPhoneApiBuilderImpl
import de.mtorials.dialphone.core.cache.InMemoryCache
import de.mtorials.dialphone.core.cache.PhoneCache
import de.mtorials.dialphone.core.cache.UserCacheListener
import kotlinx.serialization.modules.plus

class DialPhoneBuilderImpl(
    homeserverUrl: String,
) : DialPhoneBuilder, DialPhoneApiBuilderImpl(
    homeserverUrl = homeserverUrl
) {
    override var cache: PhoneCache? = null
    var encryption: Boolean = false

    override fun useEncryption() {
        encryption = true
    }

    override fun cacheInMemory() {
        cache = InMemoryCache()
    }

    suspend fun buildDialPhone(block: DialPhoneBuilder.() -> Unit) : DialPhoneImpl {
        block()
        this.configure()
        // TODO necessary here?
        val me = APIRequests(homeserverUrl = homeserverUrl, token = token!!, client = client).getMe()
        return DialPhoneImpl(
            token = this.token!!,
            homeserverUrl = homeserverUrl,
            client = client,
            ownId = me.id,
            deviceId = me.deviceId,
            initCallback = {},
            coroutineScope = coroutineScope,
            cache = cache,
            dialPhoneJson = format,
            useEncryption = encryption,
        ).also {
            it.addListeners(*listenerList.toTypedArray())
            if (cache != null) this.addListeners(UserCacheListener(
                cache!!,
                it,
            ))
        }
    }
}