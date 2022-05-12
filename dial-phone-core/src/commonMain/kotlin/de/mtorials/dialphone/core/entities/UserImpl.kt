package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.core.DialPhone
import kotlinx.serialization.SerialName

class UserImpl internal constructor(
    override val phone: DialPhone,
    override val id: UserId,
) : User {
    //override val name: String? = MatrixID.fromString(id).value
}