package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entityfutures.UserFutureImpl
import de.mtorials.dialphone.core.ids.RoomId
import de.mtorials.dialphone.core.ids.UserId

class MemberImpl(
    override val roomId: RoomId,
    override val userId: UserId,
    override val phone: DialPhone
) : Member {
    override suspend fun receive(): User = UserFutureImpl(
        userId,
        phone
    ).receive()
}