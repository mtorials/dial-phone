package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.core.ids.EventId

abstract class DialEvent (
    open val phone: DialPhoneApi,
    open val id: EventId? = null
)