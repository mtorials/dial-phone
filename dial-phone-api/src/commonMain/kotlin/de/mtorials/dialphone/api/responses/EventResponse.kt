package de.mtorials.dialphone.api.responses

import de.mtorials.dialphone.api.ids.EventId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class EventResponse (
    @SerialName("event_id")
    val id: EventId
)