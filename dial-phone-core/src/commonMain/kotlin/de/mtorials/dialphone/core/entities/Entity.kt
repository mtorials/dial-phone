package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.api.ids.MatrixID
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.DialPhoneImpl

/**
 * Represents an entity of the DialPhone SDK
 */
interface Entity {
    val id : MatrixID
    val phone: DialPhone
}