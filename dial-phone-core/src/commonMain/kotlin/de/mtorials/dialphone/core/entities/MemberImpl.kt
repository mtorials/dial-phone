package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.core.DialPhone

class MemberImpl(
    override val roomId: String,
    override val id: String,
    override val phone: DialPhone
) : de.mtorials.dialphone.core.entities.Member {
    override suspend fun receive(): de.mtorials.dialphone.core.entities.User = de.mtorials.dialphone.core.entities.entityfutures.UserFutureImpl(
        id,
        phone
    ).receive()
}