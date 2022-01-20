package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.core.ids.EventId
import de.mtorials.dialphone.core.DialPhone

class PresenceChangeEvent(
    override val id: EventId,
    val senderId: String,
    val newStatus: String,
    override val phone: DialPhone
) : DialEvent(phone, id)