package de.mtorials.dialphone.entities.entityfutures

/**
 * Represents an entity without properties
 */
interface EntityFuture<T> {
    /**
     * Receive the entity
     */
    suspend fun receive() : T
}