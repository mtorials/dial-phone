package de.mtorials.dialphone.dialevents

import de.mtorials.dialphone.sendTextMessage

suspend infix fun MessageReceivedEvent.answer(answer: String) : String = this.roomFuture.sendTextMessage(answer)