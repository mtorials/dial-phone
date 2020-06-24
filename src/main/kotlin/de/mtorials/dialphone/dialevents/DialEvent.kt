package de.mtorials.dialphone.dialevents

import de.mtorials.dialphone.DialPhone

abstract class DialEvent (
        open val id: String,
        open val phone: DialPhone
)