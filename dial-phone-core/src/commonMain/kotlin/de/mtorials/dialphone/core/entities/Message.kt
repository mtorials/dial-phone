package de.mtorials.dialphone.core.entities

import de.mtorials.dialphone.api.ids.EventId
import de.mtorials.dialphone.api.ids.RoomId
import de.mtorials.dialphone.api.model.mevents.roommessage.MRoomMessage
import de.mtorials.dialphone.core.entities.room.JoinedRoom

interface Message : Entity {
    override val id: EventId
    val roomId: RoomId
    val content: MRoomMessage.MRoomMessageContent
    val room: JoinedRoom
    val messageType: MRoomMessage.MRoomMessageEventContentType
    val author: Member

    /**
     * Redact the message event
     */
    suspend fun redact(reason: String? = null)
}