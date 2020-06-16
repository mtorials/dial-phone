package de.mtorials.dialexample

import de.mtorials.dial.entityfutures.RoomFuture
import de.mtorials.dial.listener.MatrixEventAdapter
import de.mtorials.dialexample.cutstomevents.PositionEvent

class PositionListener : MatrixEventAdapter<PositionEvent>(PositionEvent::class) {
    override fun onMatrixEvent(event: PositionEvent, roomFuture: RoomFuture) {
        println("Received position event!" + event.content.toString())
    }
}