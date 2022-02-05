package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.DialPhoneApiBuilder
import de.mtorials.dialphone.api.EventHook
import de.mtorials.dialphone.core.cache.PhoneCache

interface DialPhoneBuilder : DialPhoneApiBuilder {

    /**
     * Use a cache to reduce request made to the api
     *  @see cacheInMemory to use the standard in memory implementation
     */
    var cache: PhoneCache?

    /**
     * To inject code into the build processes, for modularity reasons
     */
    fun afterInitialization(block: DialPhoneBuilderImpl.(DialPhoneImpl) -> Unit)

    /**
     * Use the in memory cache implementation
     * @see cache to choose use a custom cache implementation
     */
    fun cacheInMemory()
}
