package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.entities.DialPhone
import de.mtorials.dialphone.entities.entities.User

interface UserFuture :
    EntityFuture<User> {
    val id: String
    val phone: DialPhone
}