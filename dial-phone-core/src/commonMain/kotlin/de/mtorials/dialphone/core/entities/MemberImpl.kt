package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entityfutures.UserFutureImpl

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