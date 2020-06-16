package de.mtorials.dialphone.events

import de.mtorials.dialphone.sendTextMessage

suspend infix fun MessageReceivedEvent.answer(answer: String) : String = this.roomFuture.sendTextMessage(answer)