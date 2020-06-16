package de.mtorials.dial.listener

import de.mtorials.dial.DialPhone
import de.mtorials.dial.entityfutures.RoomFuture
import de.mtorials.dial.mevents.MatrixEvent
import kotlin.reflect.KClass

abstract class MatrixEventAdapter<T : MatrixEvent>(private val clazz : KClass<T>) : Listener {

    abstract fun onMatrixEvent(event: T, roomFuture: RoomFuture)
    override fun onRoomEvent(event: MatrixEvent, roomId: String, phone: DialPhone) {
        if (clazz.isInstance(event)) onMatrixEvent(event as T, RoomFuture(roomId, phone))
    }
}