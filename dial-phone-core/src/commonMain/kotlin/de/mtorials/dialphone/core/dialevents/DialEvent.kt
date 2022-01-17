package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.core.DialPhone

abstract class DialEvent (
    open val phone: DialPhone,
    open val id: String? = null
)