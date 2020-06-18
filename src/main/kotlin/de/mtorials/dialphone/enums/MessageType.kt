package de.mtorials.dialphone.enums

import com.fasterxml.jackson.annotation.JsonProperty

enum class MessageType(name: String) {
    @JsonProperty("m.text")
    TEXT("m.text"),
    @JsonProperty("m.emote")
    EMOTE("m.emote"),
    @JsonProperty("m.notice")
    NOTICE("m.image"),
    @JsonProperty("m.image")
    IMAGE("a"),
    @JsonProperty("m.file")
    FILE("m.file"),
    @JsonProperty("m.audio")
    AUDIO("m.audio"),
    @JsonProperty("m.location")
    LOCATION("m.location"),
    @JsonProperty("m.video")
    VIDEO("m.video"),
}