package de.mtorials.dial.events

import de.mtorials.dial.DialPhone
import de.mtorials.dial.MatrixID

abstract class Event (
        open val id: String,
        open val phone: DialPhone
)