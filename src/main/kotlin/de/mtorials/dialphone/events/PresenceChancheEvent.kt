package de.mtorials.dialphone.events

import de.mtorials.dialphone.DialPhone

class PresenceChangeEvent(
    override val id: String,
    val senderId: String,
    val newStatus: String,
    override val phone: DialPhone
) : DialEvents(id, phone)