package de.mtorials.dialphone.api.responses.sync

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO problem in spec, type should be int
@Serializable
data class UnreadNotificationsResponse(
    @SerialName("highlight_count")
    val highlightCount: Int? = null,
    @SerialName("notification_count")
    val notificationCount: Int? = null,
)