package de.mtorials.dialphone.api.model.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MessageType(name: String) {
    @SerialName("m.text")
    TEXT("m.text"),
    @SerialName("m.emote")
    EMOTE("m.emote"),
    @SerialName("m.notice")
    NOTICE("m.image"),
    @SerialName("m.image")
    IMAGE("a"),
    @SerialName("m.file")
    FILE("m.file"),
    @SerialName("m.audio")
    AUDIO("m.audio"),
    @SerialName("m.location")
    LOCATION("m.location"),
    @SerialName("m.video")
    VIDEO("m.video"),
}