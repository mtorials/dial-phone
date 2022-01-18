package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.core.DialPhone

/**
 * Represents an entity of the DialPhone SDK
 */
interface Entity {
    val id : String
    val phone: DialPhone
}