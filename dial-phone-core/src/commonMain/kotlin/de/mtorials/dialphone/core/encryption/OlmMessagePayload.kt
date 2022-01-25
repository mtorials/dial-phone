package de.mtorials.dialphone.core.encryption

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class OlmMessagePayload(
    val type: String,
    val content: JsonElement,
    val sender: String,
    val recipient: String,
    @SerialName("recipient_keys")
    val recipientKeys: Map<String, String>,
    val keys: Map<String, String>,
)