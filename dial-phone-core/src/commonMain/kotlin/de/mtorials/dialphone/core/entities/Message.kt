package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.actions.MessageActionsImpl
import de.mtorials.dialphone.core.entityfutures.RoomFuture
import kotlin.reflect.KClass

class Message(
    val body: String,
    id: EventId,
    phone: DialPhone,
    val roomFuture: RoomFuture,
    val messageType: KClass<out MRoomMessage.MRoomMessageContent>,
    val author: Member
) : MessageActionsImpl(
    id = id,
    phone = phone,
    roomId = roomFuture.id
)