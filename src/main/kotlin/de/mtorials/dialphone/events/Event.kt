package de.mtorials.dialphone.events

import de.mtorials.dialphone.DialPhone

abstract class Event (
        open val id: String,
        open val phone: DialPhone
)