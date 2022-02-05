package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.core.DialPhone
import kotlinx.serialization.SerialName

class UserImpl(
    @SerialName("avatar_url")
    override val avatarURL: String?,
    @SerialName("displayname")
    override val displayName: String?,
    @SerialName("user_id")
    override val id: UserId,
    override val phone: DialPhone
) : User {
    //override val name: String? = MatrixID.fromString(id).value
}