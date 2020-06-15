package de.mtorials.dial.events

import de.mtorials.dial.DialPhone

class PresenceChangeEvent(
    override val id: String,
    val senderId: String,
    val newStatus: String,
    override val phone: DialPhone
) : Event(id, phone)