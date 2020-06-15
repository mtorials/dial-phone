package de.mtorials.dial.entityfutures

import de.mtorials.dial.DialPhone

abstract class EntityFuture<T>(
    val entityId: String,
    val phone: DialPhone
) {
    val requestObject = phone.requestObject
    abstract suspend fun receive() : T
}