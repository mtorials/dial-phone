package de.mtorials.dialphone.core.entities.actions

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.model.mevents.roommessage.MessageEventContent
import de.mtorials.dialphone.core.model.mevents.roomstate.StateEventContent

open class RoomActionsImpl(
    override val phone: DialPhone,
    override val id: String
) : de.mtorials.dialphone.core.entities.actions.RoomActions {

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