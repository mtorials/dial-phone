package de.mtorials.dialphone.entities.actions

import net.mt32.makocommons.mevents.ContentEventType
import de.mtorials.dialphone.DialPhone
import net.mt32.makocommons.mevents.MatrixEvent
import net.mt32.makocommons.mevents.roommessage.MessageEventContent
import net.mt32.makocommons.mevents.roomstate.StateEventContent
import kotlin.reflect.KClass

open class RoomActionsImpl(
    override val phone: DialPhone,
    override val id: String
) : RoomActions {
    override suspend fun sendMessageEvent(content: MessageEventContent) : String {
        val type : KClass<out MatrixEvent> = content::class.annotations.filterIsInstance<ContentEventType>()[0].type
        return phone.requestObject.sendMessageEvent(type, content, id)
    }

    override suspend fun sendStateEvent(content: StateEventContent, stateKey: String): String {
        val type : KClass<out MatrixEvent> = content::class.annotations.filterIsInstance<ContentEventType>()[0].type
        return phone.requestObject.sendStateEvent(type, content, id, stateKey)
    }
}