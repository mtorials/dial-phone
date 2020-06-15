package de.mtorials.dial.actions

import de.mtorials.dial.DialPhone
import de.mtorials.dial.entities.User

class UserFuture(
    id: String,
    phone: DialPhone
) : DialFuture<User>(id, phone) {
    override suspend fun complete(): User {
        return phone.requestObject.getUserById(entityId)
    }
}