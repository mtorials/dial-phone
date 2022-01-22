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

    override fun cacheInMemory() {
        cache = InMemoryCache()
    }

    suspend fun buildDialPhone(block: DialPhoneBuilder.() -> Unit) : DialPhoneImpl {
        block()
        this.configure()
        // TODO necessary here?
        val id = APIRequests(homeserverUrl = homeserverUrl, token = token!!, client = client).getMe().id
        return DialPhoneImpl(
            token = this.token!!,
            homeserverUrl = homeserverUrl,
            client = client,
            ownId = id,
            initCallback = {},
            coroutineDispatcher = coroutineDispatcher,
            cache = cache,
        ).also {
            it.addListeners(*listenerList.toTypedArray())
            if (cache != null) this.addListeners(UserCacheListener(
                cache!!,
                it,
            ))
        }
    }
}