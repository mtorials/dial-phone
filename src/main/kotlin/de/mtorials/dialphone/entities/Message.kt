package de.mtorials.dialphone.entities

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.actions.MessageActionsImpl
import de.mtorials.dialphone.entities.entityfutures.RoomFuture
import de.mtorials.dialphone.mevents.roommessage.MRoomMessage
import kotlin.reflect.KClass

class Message(
    val body: String,
    id: String,
    phone: DialPhone,
    val roomFuture: RoomFuture,
    val messageType: KClass<out MRoomMessage.MRoomMessageContent>,
    val author: Member
) : MessageActionsImpl(
    id = id,
    phone = phone,
    roomId = roomFuture.id
)