package de.mtorials.dialphone.core.entityfutures

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.core.entities.UserImpl

class UserFutureImpl(
    override val id: String,
    override val phone: DialPhone,
) : EntityFuture<User>,
    UserFuture {
    override suspend fun receive(): User {
        val response = phone.getUserById(id)
            ?: throw Error("Cant find user corresponding to EntityFuture")
        return UserImpl(
            id = id,
            displayName = response.displayName,
            avatarURL = response.avatarURL,
            phone = phone
        )
    }
}