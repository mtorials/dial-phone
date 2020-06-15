package de.mtorials.dial

import de.mtorials.dial.entityfutures.RoomFuture

suspend infix fun RoomFuture.send(message: String) = this.sendMessage(message)