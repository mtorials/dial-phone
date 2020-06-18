package de.mtorials.example

import de.mtorials.dialphone.entities.entityfutures.RoomFutureImpl
import de.mtorials.dialphone.listener.MatrixEventAdapter
import de.mtorials.example.cutstomevents.PositionEvent

class PositionListener : MatrixEventAdapter<PositionEvent>(PositionEvent::class) {
    override fun onMatrixEvent(event: PositionEvent, roomFuture: RoomFutureImpl) {
        println("Received position event!" + event.content.toString())
    }
}