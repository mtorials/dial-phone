package de.mtorials.dialphone.entities

import de.mtorials.dialphone.DialPhone

/**
 * Represents an entity of the DialPhone SDK
 */
interface Entity {
    val id : String
    val phone: DialPhone
}