package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.User

class UserFuture(
    id: String,
    phone: DialPhone
) : EntityFuture<User>(id, phone) {
    override suspend fun receive(): User {
        return phone.requestObject.getUserById(entityId)
    }
}