package de.mtorials.example

import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.listener.MatrixEventAdapter
import de.mtorials.dialphone.mevents.roommessage.MatrixMessageEvent
import de.mtorials.dialphone.rename

class MessageEventListener() : MatrixEventAdapter<MatrixMessageEvent>(MatrixMessageEvent::class) {
    override fun onMatrixEvent(event: MatrixMessageEvent, roomFuture: RoomFutureImpl) {
        println("Received message event ${event::class.simpleName}: ${event.content}")
    }
}