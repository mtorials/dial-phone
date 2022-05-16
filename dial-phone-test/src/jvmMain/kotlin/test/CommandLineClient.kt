package test

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.room.JoinedRoom
import de.mtorials.dialphone.core.listeners.ListenerAdapter
import de.mtorials.dialphone.core.sendTextMessage
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {

    var activeRoom: JoinedRoom? = null

    val phone = DialPhone(MATRIX_SERVER) {
        asUser("name", "password")
        addListeners(ListenerAdapter {
            onRoomMessageReceived listener@{
                if (activeRoom?.id != it.room.id) return@listener
                it.run { println("${room.name} :: ${message.author.displayName ?: message.author.id} :: ${message.content.body}") }
            }
        })
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