package de.mtorials.dialphone.core.entities.actions

import de.mtorials.dialphone.core.DialPhone

interface MessageActions {

    val phone: DialPhone
    val id: String
    val roomId: String

    suspend fun redact(reason: String? = null)
    //fun react()
}