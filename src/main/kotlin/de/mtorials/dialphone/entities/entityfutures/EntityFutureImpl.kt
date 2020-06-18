package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.DialPhone

abstract class EntityFutureImpl<T>(
    val entityId: String,
    val phone: DialPhone
) : EntityFuture<T> {
    val requestObject = phone.requestObject
}