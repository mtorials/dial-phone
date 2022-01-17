package de.mtorials.dialphone.entities.actions

import de.mtorials.dialphone.entities.DialPhone

interface MessageActions {

    val phone: DialPhone
    val id: String
    val roomId: String

    suspend fun redact(reason: String? = null)
    //fun react()
}