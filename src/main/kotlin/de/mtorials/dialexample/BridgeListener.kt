package de.mtorials.dialexample

import de.mtorials.dial.DialPhone
import de.mtorials.dial.MatrixID
import de.mtorials.dial.events.MessageReceivedEvent
import de.mtorials.dial.listener.ListenerAdapter
import de.mtorials.kda.KDA
import de.mtorials.kda.MessageEventContext
import de.mtorials.kda.message.InfixEmbedBuilder

class BridgeListener(
    private val kda: KDA,
    private val phone: DialPhone
) : ListenerAdapter() {
    override suspend fun onRoomMessageReceive(event: MessageReceivedEvent) {
        if (event.senderId == event.phone.ownId) return
        println("user ${event.senderId} send ${event.content.body}")

        bridgedRoomIDAndServerIDChannelIDPair
            .filter { (_, roomID) -> roomID == event.roomFuture.entityId }
            .forEach { ( channel, _) ->
                val (guildID, channelID) = channel
                kda.jda.getGuildById(guildID)?.getTextChannelById(channelID)?.sendMessage(InfixEmbedBuilder {
                    message title MatrixID.fromString(event.senderId).value
                    message description event.content.body
                }.build())?.queue() ?: throw RuntimeException("channel or guild null!")
            }
    }
}