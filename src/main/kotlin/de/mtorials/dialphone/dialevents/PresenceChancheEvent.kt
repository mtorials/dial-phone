package de.mtorials.dialphone.dialevents

import de.mtorials.dialphone.DialPhone

class PresenceChangeEvent(
    override val id: String,
    val senderId: String,
    val newStatus: String,
    override val phone: DialPhone
) : DialEvent(id, phone)