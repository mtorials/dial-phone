package de.mtorials.example

import de.mtorials.dialphone.entities.entityfutures.RoomFuture
import de.mtorials.dialphone.listener.MatrixEventAdapter
import de.mtorials.dialphone.mevents.roomstate.MatrixStateEvent

class StateListener : MatrixEventAdapter<MatrixStateEvent>(MatrixStateEvent::class) {
    override fun onMatrixEvent(event: MatrixStateEvent, roomFuture: RoomFuture) {
        println("Received state event ${event::class.simpleName}. Content is ${event.content}. State key is ${event.stateKey}")
    }
}