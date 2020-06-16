package de.mtorials.dialphone.entities

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.entityfutures.UserFuture

class MemberImpl(
    override val user: UserFuture,
    override val displayName: String?,
    override val avatarUrl: String?,
    override val phone: DialPhone
) : Member {
    override val id = user.entityId
}