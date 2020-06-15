package de.mtorials.dial.enums

import com.fasterxml.jackson.annotation.JsonProperty

enum class Membership {
    @JsonProperty("join")
    JOIN,
    @JsonProperty("leave")
    LEAVE,
    @JsonProperty("invite")
    INVITE,
    @JsonProperty("ban")
    BAN,

    /**
     * Reserved in specification (https://matrix.org/docs/spec/client_server/r0.6.1#m-room-member)
     */
    @JsonProperty("knock")
    KNOCK
}