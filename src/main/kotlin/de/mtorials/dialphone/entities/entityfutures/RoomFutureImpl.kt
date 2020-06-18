package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.ContentEventType
import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.Room
import de.mtorials.dialphone.entities.RoomImpl
import de.mtorials.dialphone.entities.actions.RoomActions
import de.mtorials.dialphone.mevents.MatrixEvent
import de.mtorials.dialphone.mevents.roommessage.MessageEventContent
import de.mtorials.dialphone.mevents.roomstate.MatrixStateEvent
import de.mtorials.dialphone.mevents.roomstate.StateEventContent
import kotlin.reflect.KClass

class RoomFutureImpl(
    roomID: String,
    phone: DialPhone
) : EntityFutureImpl<Room>(roomID, phone), RoomFuture {
    override suspend fun receiveStateEvents(): Array<MatrixStateEvent>  = requestObject.getRoomsState(entityId)
    override suspend fun receive() : Room = RoomImpl(this, receiveStateEvents())
    override suspend fun sendMessageEvent(content: MessageEventContent) : String {
        val type : KClass<out MatrixEvent> = content::class.annotations.filterIsInstance<ContentEventType>()[0].type
        return phone.requestObject.sendMessageEvent(type, content, entityId)
    }

    override suspend fun sendStateEvent(content: StateEventContent, stateKey: String): String {
        val type : KClass<out MatrixEvent> = content::class.annotations.filterIsInstance<ContentEventType>()[0].type
        return phone.requestObject.sendStateEvent(type, content, entityId, stateKey)
    }
}