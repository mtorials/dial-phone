package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.DialPhone

abstract class EntityFuture<T>(
    val entityId: String,
    val phone: DialPhone
) {
    val requestObject = phone.requestObject
    abstract suspend fun receive() : T
}