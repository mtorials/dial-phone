package de.mtorials.dialphone.core.actions

import de.mtorials.dialphone.api.model.mevents.roommessage.MessageEventContent
import de.mtorials.dialphone.api.model.mevents.roomstate.StateEventContent
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.ids.RoomId

open class RoomActionsImpl(
    override val phone: DialPhone,
    override val id: RoomId
) : RoomActions {

    override suspend fun sendMessageEvent(content: MessageEventContent, eventType: String) : String {
        return phone.apiRequests.sendMessageEvent(eventType, content, id.toString())
    }

    override suspend fun sendStateEvent(content: StateEventContent, eventType: String, stateKey: String): String {
        return phone.apiRequests.sendStateEvent(eventType, content, id.toString(), stateKey)
    }

    override suspend fun leave() {
        phone.apiRequests.leaveRoomWithId(id.toString())
    }
}