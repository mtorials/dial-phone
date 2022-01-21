package de.mtorials.dialphone.api.responses.sync

import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.Serializable

@Serializable
class AccountData {
    val events: List<MatrixEvent> = listOf()
}