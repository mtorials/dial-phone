package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.ContentEventType
import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.Room
import de.mtorials.dialphone.entities.RoomImpl
import de.mtorials.dialphone.entities.actions.RoomActions
import de.mtorials.dialphone.mevents.EventContent
import de.mtorials.dialphone.mevents.MatrixEvent
import de.mtorials.dialphone.mevents.roommessage.MatrixMessageEvent
import de.mtorials.dialphone.mevents.roommessage.MessageEventContent
import de.mtorials.dialphone.mevents.roomstate.StateEventContent
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

class RoomFuture(
    roomID: String,
    phone: DialPhone
) : EntityFuture<Room>(roomID, phone), RoomActions {
    override suspend fun receive() : Room = RoomImpl(this, requestObject.getRoomsState(entityId))
    override suspend fun sendMessageEvent(content: MessageEventContent) : String {
        val type : KClass<out MatrixEvent> = content::class.annotations.filterIsInstance<ContentEventType>()[0].type
        return phone.requestObject.sendMessageEvent(type, content, entityId)
    }

    override suspend fun sendStateEvent(content: StateEventContent, stateKey: String): String {
        val type : KClass<out MatrixEvent> = content::class.annotations.filterIsInstance<ContentEventType>()[0].type
        return phone.requestObject.sendStateEvent(type, content, entityId, stateKey)
    }
}