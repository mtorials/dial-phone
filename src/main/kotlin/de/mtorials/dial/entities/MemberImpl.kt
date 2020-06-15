package de.mtorials.dial.entities

import de.mtorials.dial.DialPhone
import de.mtorials.dial.entityfutures.UserFuture

class MemberImpl(
    override val user: UserFuture,
    override val displayName: String?,
    override val avatarUrl: String?,
    override val phone: DialPhone
) : Member {
    override val id = user.entityId
}