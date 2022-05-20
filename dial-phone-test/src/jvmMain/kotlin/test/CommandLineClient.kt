package test

import de.mtorials.dialphone.api.logging.DialPhoneLogLevel
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.room.JoinedRoom
import de.mtorials.dialphone.core.listeners.ListenerAdapter
import de.mtorials.dialphone.core.sendTextMessage
import de.mtorials.dialphone.encyption.useEncryption
import io.ktor.client.plugins.logging.*
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {

    var activeRoom: JoinedRoom? = null

    val phone = DialPhone(MATRIX_SERVER) {
        asUser(changeConfig.username, changeConfig.password)
        useEncryption("./")
        addListeners(ListenerAdapter {
            onRoomMessageReceived listener@{
                if (activeRoom?.id != it.room.id) return@listener
                it.run { println("${room.name} :: ${message.author.displayName ?: message.author.id} :: ${message.content.body}") }
            }
        })
        dialPhoneLogLevel = DialPhoneLogLevel.TRACE
        ktorLogLevel = LogLevel.BODY
    }.apply { sync() }

    while(true) {
        val input = readLine() ?: continue
        when (input) {
            "!rooms" -> phone.getJoinedRooms().run {
                forEachIndexed { index, joinedRoom ->
                    println("$index: ${joinedRoom.name}")
                }
                println("Select room by number: ")
                activeRoom = this[readLine()?.toInt() ?: return@run]
                println("---- Selected room is ${activeRoom?.name} ----")
            }
            else -> activeRoom?.sendTextMessage(input)
        }
    }
}