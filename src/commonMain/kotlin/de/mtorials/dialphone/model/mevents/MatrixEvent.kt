package de.mtorials.dialphone.model.mevents

/**
 * Representation of a matrix event
 */
interface MatrixEvent {
    val sender: String
    val content: EventContent
}