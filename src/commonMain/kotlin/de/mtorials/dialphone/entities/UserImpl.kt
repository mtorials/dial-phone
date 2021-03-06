package de.mtorials.dialphone.entities

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.MatrixID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserImpl(
    @SerialName("avatar_url")
    override val avatarURL: String?,
    @SerialName("displayname")
    override val displayName: String?,
    @SerialName("user_id")
    override val id: String,
    override val phone: DialPhone
) : User {
    override val name: String? = MatrixID.fromString(id).value
}