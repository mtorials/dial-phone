package de.mtorials.dialphone.entities.entities

import de.mtorials.dialphone.core.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.entities.DialPhone
import de.mtorials.dialphone.entities.actions.MessageActionsImpl
import de.mtorials.dialphone.entities.entityfutures.RoomFuture
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