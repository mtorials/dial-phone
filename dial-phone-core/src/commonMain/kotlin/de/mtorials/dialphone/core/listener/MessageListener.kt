package de.mtorials.dialphone.core.listener

import de.mtorials.dialphone.core.dialevents.MessageReceivedEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MessageListener(
    receiveOld: Boolean = true,
    val onNewMessage: suspend (MessageReceivedEvent) -> Unit = {}
) : ListenerAdapter(receivePastEvents = receiveOld) {

    private val messagesByRoom: MutableMap<String, MutableList<de.mtorials.dialphone.core.entities.Message>> = mutableMapOf()

    override suspend fun onRoomMessageReceive(event: MessageReceivedEvent) {
        if (messagesByRoom[event.roomFuture.id] == null) messagesByRoom[event.roomFuture.id] = mutableListOf()
        messagesByRoom[event.roomFuture.id]?.add(event.message)
        GlobalScope.launch { onNewMessage(event) }
    }

    /**
     * Returns the messages for a given room
     */
    fun getMessages(roomId: String) : List<de.mtorials.dialphone.core.entities.Message> = messagesByRoom[roomId] ?: listOf()
}