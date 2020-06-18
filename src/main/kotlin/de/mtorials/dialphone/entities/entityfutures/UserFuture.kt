package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.User

class UserFuture(
    val id: String,
    val phone: DialPhone
) : EntityFuture<User> {
    override suspend fun receive(): User {
        return phone.requestObject.getUserById(id)
    }
}