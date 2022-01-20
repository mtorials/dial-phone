package de.mtorials.dialphone.core.entityfutures

import de.mtorials.dialphone.core.ids.UserId
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.User
import de.mtorials.dialphone.core.entities.UserImpl

class UserFutureImpl(
    override val userId: UserId,
    override val phone: DialPhone,
) : EntityFuture<User>,
    UserFuture {
    override suspend fun receive(): User {
        val response = phone.getUserById(userId)
            ?: throw Error("Cant find user corresponding to EntityFuture")
        return UserImpl(
            id = userId,
            displayName = response.displayName,
            avatarURL = response.avatarURL,
            phone = phone
        )
    }
}