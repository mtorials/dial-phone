package de.mtorials.dialphone.events

import de.mtorials.dialphone.DialPhone

abstract class DialEvents (
        open val id: String,
        open val phone: DialPhone
)