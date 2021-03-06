package de.mtorials.dialphone.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class EventResponse (
    @SerialName("event_id")
    val id: String
)