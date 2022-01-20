package de.mtorials.dialphone.core.entityfutures

/**
 * Represents an entity without properties
 */
interface EntityFuture<T> {
    /**
     * Receive the entity
     */
    suspend fun receive() : T
}