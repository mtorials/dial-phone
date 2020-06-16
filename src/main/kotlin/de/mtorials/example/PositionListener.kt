package de.mtorials.example

import de.mtorials.dialphone.entities.entityfutures.RoomFuture
import de.mtorials.dialphone.listener.MatrixEventAdapter
import de.mtorials.example.cutstomevents.PositionEvent

class PositionListener : MatrixEventAdapter<PositionEvent>(PositionEvent::class) {
    override fun onMatrixEvent(event: PositionEvent, roomFuture: RoomFuture) {
        println("Received position event!" + event.content.toString())
    }
}