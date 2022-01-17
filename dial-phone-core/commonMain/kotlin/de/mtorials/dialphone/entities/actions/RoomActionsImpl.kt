package de.mtorials.dialphone.entities.actions

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.model.mevents.roommessage.MessageEventContent
import de.mtorials.dialphone.model.mevents.roomstate.StateEventContent

open class RoomActionsImpl(
    override val phone: DialPhone,
    override val id: String
) : RoomActions {

    override suspend fun sendMessageEvent(content: MessageEventContent, eventType: String) : String {
        return phone.requestObject.sendMessageEvent(eventType, content, id)
    }

    override suspend fun sendStateEvent(content: StateEventContent, eventType: String, stateKey: String): String {
        return phone.requestObject.sendStateEvent(eventType, content, id, stateKey)
    }

    override suspend fun leave() {
        phone.requestObject.leaveRoomWithId(id)
    }
}