package de.mtorials.dpexample

import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.listener.MatrixEventAdapter
import net.mt32.makocommons.mevents.roommessage.MatrixMessageEvent

class MessageEventListener : MatrixEventAdapter<MatrixMessageEvent>(MatrixMessageEvent::class) {
    override fun onMatrixEvent(event: MatrixMessageEvent, roomFuture: RoomFutureImpl) {
        println("Received message event ${event::class.simpleName}: ${event.content}")
    }
}