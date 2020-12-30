package de.mtorials.dialphone.entities.actions

import net.mt32.makocommons.mevents.ContentEventType
import de.mtorials.dialphone.DialPhone
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import net.mt32.makocommons.mevents.MatrixEvent
import net.mt32.makocommons.mevents.roommessage.MessageEventContent
import net.mt32.makocommons.mevents.roomstate.StateEventContent
import kotlin.reflect.KClass

open class RoomActionsImpl(
    override val phone: DialPhone,
    override val id: String
) : RoomActions {

    override suspend fun sendMessageEvent(content: MessageEventContent, typeName: String) : String {
        return phone.requestObject.sendMessageEvent(typeName, content, id)
    }

    override suspend fun sendStateEvent(content: StateEventContent, typeName: String, stateKey: String): String {
        return phone.requestObject.sendStateEvent(typeName, content, id, stateKey)
    }
}