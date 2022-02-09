package de.mtorials.dialphone.core.listeners

import de.mtorials.dialphone.core.EventCallback
import de.mtorials.dialphone.core.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.core.entities.MessageImpl

class MessageListener(
    receiveOld: Boolean = true,
    val onNewMessage: suspend (MessageReceivedEvent) -> Unit = {}
) : ListenerAdapterImpl(receiveOld) {

    private val messagesByRoom: MutableMap<String, MutableList<MessageImpl>> = mutableMapOf()

    override var callbackOnMessageReceived: EventCallback<MessageReceivedEvent> = { event ->
        if (messagesByRoom[event.roomFuture.id.toString()] == null) messagesByRoom[event.roomFuture.id.toString()] = mutableListOf()
        messagesByRoom[event.roomFuture.id.toString()]?.add(event.message)
        onNewMessage(event)
    }

    /**
     * Returns the messages for a given room
     */
    fun getMessages(roomId: String) : List<MessageImpl> = messagesByRoom[roomId] ?: listOf()
}