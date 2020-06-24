package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.User
import de.mtorials.dialphone.entities.UserImpl

class UserFuture(
    val id: String,
    val phone: DialPhone
) : EntityFuture<User> {
    override suspend fun receive(): User {
        val response = phone.requestObject.getUserById(id)
            ?: throw Error("Cant find user to future")
        return UserImpl(
            id = id,
            displayName = response.displayName,
            avatarURL = response.avatarURL,
            phone = phone
        )
    }
}