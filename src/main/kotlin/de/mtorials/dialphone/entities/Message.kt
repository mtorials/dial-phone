package de.mtorials.dialphone.entities

import de.mtorials.dialphone.mevents.roommessage.MRoomMessage
import kotlin.reflect.KClass

class Message (
    val body: String,
    val messageType: KClass<out MRoomMessage.MRoomMessageContent>
)