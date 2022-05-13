package de.mtorials.dialphone.api.model.mevents.todevice

import de.mtorials.dialphone.api.ids.UserId
import de.mtorials.dialphone.api.model.mevents.EventContent
import de.mtorials.dialphone.api.model.mevents.MatrixEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName(MDummy.EVENT_TYPE)
data class MDummy(
    override val content: MDummyContent,
) : MatrixEvent {
    override val sender: UserId? = null

    override fun getTypeName(): String = EVENT_TYPE

    companion object {
        const val EVENT_TYPE = "m.dummy"
    }

    @Serializable
    class MDummyContent : EventContent
}