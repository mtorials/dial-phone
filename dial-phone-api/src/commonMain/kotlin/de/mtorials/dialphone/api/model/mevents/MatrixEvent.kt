package de.mtorials.dialphone.api.model.mevents

import de.mtorials.dialphone.api.ids.UserId

/**
 * Representation of a matrix event
 */
interface MatrixEvent {
    val sender: UserId?
    val content: EventContent

    fun getTypeName() : String
}