package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.api.DialPhoneApi

abstract class DialEvent (
    open val phone: DialPhoneApi,
    open val id: String? = null
)