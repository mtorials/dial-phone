package de.mtorials.dial

import de.mtorials.dial.actions.RoomFuture

suspend infix fun RoomFuture.send(message: String) = this.sendMessage(message)