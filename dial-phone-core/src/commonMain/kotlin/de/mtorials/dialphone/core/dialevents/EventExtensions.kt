package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.core.sendTextMessage

suspend infix fun MessageReceivedEvent.answer(answer: String) : String = this.roomFuture.sendTextMessage(answer)