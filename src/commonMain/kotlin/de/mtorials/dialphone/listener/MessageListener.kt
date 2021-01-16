package de.mtorials.dialphone.listener

import de.mtorials.dialphone.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.entities.Message

class MessageListener(
    receiveOld: Boolean = true,
    val onNewMessage: (MessageReceivedEvent) -> Unit = {}
) : ListenerAdapter(receivePastEvents = receiveOld) {

    private val messagesByRoom: MutableMap<String, MutableList<Message>> = mutableMapOf()

    override suspend fun onRoomMessageReceive(event: MessageReceivedEvent) {
        if (messagesByRoom[event.roomFuture.id] == null) messagesByRoom[event.roomFuture.id] = mutableListOf()
        messagesByRoom[event.roomFuture.id]?.add(event.message)
        onNewMessage(event)
    }

    /**
     * Returns the messages for a given room
     */
    fun getMessages(roomId: String) : List<Message> = messagesByRoom[roomId] ?: listOf()
}