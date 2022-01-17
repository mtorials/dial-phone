package de.mtorials.dialphone.model.mevents

import kotlinx.serialization.Serializable

/**
 * Representation of a matrix event
 */
interface MatrixEvent {
    val sender: String
    val content: EventContent
}