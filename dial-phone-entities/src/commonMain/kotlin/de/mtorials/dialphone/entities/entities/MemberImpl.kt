package de.mtorials.dialphone.entities.entities

import de.mtorials.dialphone.entities.DialPhone
import de.mtorials.dialphone.entities.entityfutures.UserFutureImpl

class MemberImpl(
    override val roomId: String,
    override val id: String,
    override val phone: DialPhone
) : Member {
    override suspend fun receive(): User = UserFutureImpl(
        id,
        phone
    ).receive()
}