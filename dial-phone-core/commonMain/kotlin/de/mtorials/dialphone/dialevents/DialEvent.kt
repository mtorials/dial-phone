package de.mtorials.dialphone.dialevents

import de.mtorials.dialphone.DialPhone

abstract class DialEvent (
    open val phone: DialPhone,
    open val id: String? = null
)