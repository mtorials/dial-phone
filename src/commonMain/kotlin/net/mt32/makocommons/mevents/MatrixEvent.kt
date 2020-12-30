package net.mt32.makocommons.mevents

/**
 * Representation of a matrix event
 */
interface MatrixEvent {
    val sender: String
    val content: EventContent
}