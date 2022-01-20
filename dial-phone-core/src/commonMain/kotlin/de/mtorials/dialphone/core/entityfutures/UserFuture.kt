package de.mtorials.dialphone.core.entityfutures

import de.mtorials.dialphone.core.ids.UserId
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.User

interface UserFuture : EntityFuture<User> {
    val userId: UserId
    val phone: DialPhone
}