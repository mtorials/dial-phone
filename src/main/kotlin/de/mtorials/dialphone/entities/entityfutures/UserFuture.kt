package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.User

interface UserFuture : EntityFuture<User> {
    val id: String
    val phone: DialPhone
}