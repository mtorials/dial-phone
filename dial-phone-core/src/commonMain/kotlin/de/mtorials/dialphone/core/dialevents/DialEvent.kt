package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.core.DialPhoneCore

abstract class DialEvent (
    open val phone: DialPhoneCore,
    open val id: String? = null
)