package de.mtorials.dialphone.core.entities.entityfutures

import de.mtorials.dialphone.core.DialPhone

class UserFutureImpl(
    override val id: String,
    override val phone: DialPhone
) : de.mtorials.dialphone.core.entities.entityfutures.EntityFuture<de.mtorials.dialphone.core.entities.User>,
    de.mtorials.dialphone.core.entities.entityfutures.UserFuture {
    override suspend fun receive(): de.mtorials.dialphone.core.entities.User {
        val response = phone.getUserById(id)
            ?: throw Error("Cant find user corresponding to EntityFuture")
        return de.mtorials.dialphone.core.entities.UserImpl(
            id = id,
            displayName = response.displayName,
            avatarURL = response.avatarURL,
            phone = phone
        )
    }
}