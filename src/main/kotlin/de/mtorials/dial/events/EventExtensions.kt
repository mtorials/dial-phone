package de.mtorials.dial.events

suspend infix fun MessageReceivedEvent.answer(answer: String) : String = this.roomFuture.sendMessage(answer)