package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.core.DialPhoneCore

class PresenceChangeEvent(
    override val id: String,
    val senderId: String,
    val newStatus: String,
    override val phone: DialPhoneCore
) : DialEvent(phone, id)