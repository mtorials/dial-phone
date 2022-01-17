package de.mtorials.dialphone.core.entities.entityfutures

import de.mtorials.dialphone.core.DialPhone

interface UserFuture :
    de.mtorials.dialphone.core.entities.entityfutures.EntityFuture<de.mtorials.dialphone.core.entities.User> {
    val id: String
    val phone: DialPhone
}