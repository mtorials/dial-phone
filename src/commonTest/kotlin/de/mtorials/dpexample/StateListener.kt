package de.mtorials.dpexample

import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.listener.MatrixEventAdapter
import net.mt32.makocommons.mevents.roomstate.MatrixStateEvent

class StateListener : MatrixEventAdapter<MatrixStateEvent>(MatrixStateEvent::class) {
    override fun onMatrixEvent(event: MatrixStateEvent, roomFuture: RoomFutureImpl) {
        println("Received state event ${event::class.simpleName}. Content is ${event.content}. State key is ${event.stateKey}")
    }
}