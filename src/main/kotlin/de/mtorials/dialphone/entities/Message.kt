package de.mtorials.dialphone.entities

import de.mtorials.dialphone.enums.MessageType

class Message (
    val body: String,
    val messageType: MessageType
)