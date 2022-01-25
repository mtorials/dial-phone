package de.mtorials.dialphone.core.actions

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.api.ids.RoomId

interface MessageActions {

    val phone: DialPhone
    val id: EventId
    val roomId: RoomId

    suspend fun redact(reason: String? = null)
    //fun react()
}