package de.mtorials.dialphone.listener

import de.mtorials.dialphone.dialevents.MessageReceivedEvent
import de.mtorials.dialphone.entities.Message

class MessageListener(
    val onNewMessage: (MessageReceivedEvent) -> Unit = {}
) : ListenerAdapter(receivePastEvents = true) {

    private val messagesByRoom: MutableMap<String, MutableList<Message>> = mutableMapOf()

    override suspend fun onRoomMessageReceive(event: MessageReceivedEvent) {
        messagesByRoom.putIfAbsent(event.roomFuture.id, mutableListOf())
        messagesByRoom[event.roomFuture.id]?.add(event.message)
        onNewMessage(event)
    }

    /**
     * Returns the messages for a given room
     */
    fun getMessages(roomId: String) : List<Message> = messagesByRoom[roomId] ?: listOf()
}