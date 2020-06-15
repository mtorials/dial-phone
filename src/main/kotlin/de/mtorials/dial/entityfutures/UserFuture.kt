package de.mtorials.dial.entityfutures

import de.mtorials.dial.DialPhone
import de.mtorials.dial.entities.User

class UserFuture(
    id: String,
    phone: DialPhone
) : EntityFuture<User>(id, phone) {
    override suspend fun receive(): User {
        return phone.requestObject.getUserById(entityId)
    }
}