package de.mtorials.dialphone.core.dialevents

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.core.sendTextMessage

suspend infix fun MessageReceivedEvent.answer(answer: String) : EventId = this.roomFuture.sendTextMessage(answer)