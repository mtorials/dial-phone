package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.model.mevents.roommessage.MRoomMessage
import kotlin.reflect.KClass

class Message(
    val body: String,
    id: String,
    phone: DialPhone,
    val roomFuture: de.mtorials.dialphone.core.entities.entityfutures.RoomFuture,
    val messageType: KClass<out MRoomMessage.MRoomMessageContent>,
    val author: de.mtorials.dialphone.core.entities.Member
) : de.mtorials.dialphone.core.entities.actions.MessageActionsImpl(
    id = id,
    phone = phone,
    roomId = roomFuture.id
)