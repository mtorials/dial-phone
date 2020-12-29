package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.User
import de.mtorials.dialphone.entities.UserImpl

class UserFutureImpl(
    override val id: String,
    override val phone: DialPhone
) : EntityFuture<User>, UserFuture {
    override suspend fun receive(): User {
        val response = phone.getUserById(id)
            ?: throw Error("Cant find user to future")
        return UserImpl(
            id = id,
            displayName = response.displayName,
            avatarURL = response.avatarURL,
            phone = phone
        )
    }
}