package de.mtorials.dialphone.core

import de.mtorials.dialphone.api.DialPhoneApiBuilder
import de.mtorials.dialphone.api.EventHook
import de.mtorials.dialphone.core.cache.PhoneCache
import de.mtorials.dialphone.core.cache.StateCache
import de.mtorials.dialphone.core.cache.TimelineCache

interface DialPhoneBuilder : DialPhoneApiBuilder {

    /**
     * Used to cache the (matrix) state of rooms
     * Default is the standard in memory implementation
     */
    var stateCache: StateCache?

    /**
     * Used to cache the (matrix) state of rooms
     * Default is the standard in memory implementation
     */
    var timelineCache: TimelineCache?

    /**
     * To inject code into the build processes, for modularity reasons
     */
    fun afterInitialization(block: DialPhoneBuilderImpl.(DialPhoneImpl) -> Unit)
}
