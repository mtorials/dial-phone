package test

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.dialevents.answer
import de.mtorials.dialphone.core.listeners.ListenerAdapter
import de.mtorials.dialphone.core.sendTextMessage
import io.ktor.client.features.logging.*
import kotlinx.coroutines.runBlocking

const val LOCAL_ADDR = "http://localhost:80"
const val REMOTE_ADDR = "https://matrix.mtorials.de"
const val ADDR = LOCAL_ADDR

fun main() {
    runBlocking {
        println("Starting....")
        DialPhoneApi(ADDR) {
            asUser("tes", "tes", true)
            ktorLogLevel = LogLevel.NONE
        }
        val phone = DialPhone(ADDR) {
            asUser("superman", "test", true)
//            addListeners(MessageListener(false) { event ->
//                event.run { println("${message.author.userId} : ${message.body}") }
//            })
            addListeners(ListenerAdapter {
                onRoomInvited { event -> event.room.join() }
            })
            //useEncryption()
            ktorLogLevel = LogLevel.NONE
        }
//        phone.getJoinedRoomFutures().forEachIndexed { index, joinedRoom ->
//            println("$index : ${joinedRoom.name}")
//            joinedRoom.run { println("The room with name $name and id $id") }
//        }

        val myListener = ListenerAdapter {
            onRoomInvited { event ->
                val room = event.room.join()
                room.sendTextMessage("Hey, I join everywhere!")
            }
            onRoomMessageReceived { event ->
                if (event.message.content.body == "ping") event answer "pong to ${event.room.name}!"
            }
        }
        val room = phone.createRoom("The Riders") {
            makePublic()
            topic = "please come and ride with me"
        }

        room.run { println("Created a room with the name $name and the member(s) ${members.map { it.displayName }.joinToString(", ")}.") }

        println("Created room, start syncing...")
        phone.sync()
        while(true) {
            val msg = readln()
            if (msg == "exit") break
            if (msg == "!ping") phone.getJoinedRooms().forEach {
                it.sendTextMessage("ping!")
            }
//            else room.sendTextMessage(msg)
            println()
        }
    }
}