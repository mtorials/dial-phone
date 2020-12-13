package net.mt32.makocommons.enums

import com.fasterxml.jackson.annotation.JsonProperty

enum class JoinRule {
    @JsonProperty("public")
    PUBLIC,
    @JsonProperty("knock")
    KNOCK,
    @JsonProperty("invite")
    INVITE,
    @JsonProperty("private")
    PRIVATE
}