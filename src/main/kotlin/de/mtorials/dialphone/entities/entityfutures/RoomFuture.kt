package de.mtorials.dialphone.entities.entityfutures

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.Room
import de.mtorials.dialphone.entities.RoomImpl
import de.mtorials.dialphone.entities.actions.RoomActions
import de.mtorials.dialphone.mevents.EventContent
import de.mtorials.dialphone.mevents.MatrixEvent
import kotlin.reflect.KClass

class RoomFuture(
    roomID: String,
    phone: DialPhone
) : EntityFuture<Room>(roomID, phone), RoomActions {
    override suspend fun receive() : Room = RoomImpl(this, requestObject.getRoomsState(entityId))
    override suspend fun sendEvent(eventType: KClass<out MatrixEvent>, content: EventContent) : String =
        phone.requestObject.sendEvent(eventType, content, entityId)
}