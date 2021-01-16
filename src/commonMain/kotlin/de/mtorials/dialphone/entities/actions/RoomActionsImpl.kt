package de.mtorials.dialphone.entities.actions

import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.model.mevents.roommessage.MessageEventContent
import de.mtorials.dialphone.model.mevents.roomstate.StateEventContent

open class RoomActionsImpl(
    override val phone: DialPhone,
    override val id: String
) : RoomActions {

    override suspend fun sendMessageEvent(content: MessageEventContent, typeName: String) : String {
        return phone.requestObject.sendMessageEvent(typeName, content, id)
    }

    override suspend fun sendStateEvent(content: StateEventContent, typeName: String, stateKey: String): String {
        return phone.requestObject.sendStateEvent(typeName, content, id, stateKey)
    }
}