package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.MatrixID
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
) : de.mtorials.dialphone.core.entities.User {
    override val name: String? = MatrixID.fromString(id).value
}