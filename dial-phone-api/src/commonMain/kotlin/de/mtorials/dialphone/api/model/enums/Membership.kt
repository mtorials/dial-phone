package de.mtorials.dialphone.api.model.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Membership {
    @SerialName("join")
    JOIN,
    @SerialName("leave")
    LEAVE,
    @SerialName("invite")
    INVITE,
    @SerialName("ban")
    BAN,

    /**
     * Reserved in specification (https://matrix.org/docs/spec/client_server/r0.6.1#m-room-member)
     */
    @SerialName("knock")
    KNOCK
}