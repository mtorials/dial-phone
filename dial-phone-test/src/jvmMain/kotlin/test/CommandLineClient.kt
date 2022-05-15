package test

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.entities.room.JoinedRoom
import de.mtorials.dialphone.core.listeners.ListenerAdapter
import de.mtorials.dialphone.core.sendTextMessage
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {

    var activeRoom: JoinedRoom? = null

    val phone = DialPhone(MATRIX_SERVER) {
        withToken(changeConfig.token)
        addListeners(ListenerAdapter {
            onRoomMessageReceived listener@{
                if (activeRoom?.id != it.room.id) return@listener
                it.run { println("${room.name} :: ${message.author.displayName ?: message.author.id} :: ${message.content.body}") }
            }
        })
    }.apply { sync() }

    while(true) {
        val input = readLine() ?: continue
        var list = phone.getJoinedRooms()
        when {
            input == "!rooms" -> phone.getJoinedRooms().also { list = it }.forEachIndexed { index, joinedRoom ->
                println("$index ${joinedRoom.name}")
            }
            input.startsWith("!room") -> input.split(" ")[1].also { index ->
                activeRoom = list[index.toInt()]
                println("---- ${activeRoom?.name} ----")
            }
            else -> activeRoom?.sendTextMessage(input)
        }
    }
}