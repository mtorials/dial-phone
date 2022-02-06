package test

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.dialevents.answer
import de.mtorials.dialphone.core.listeners.ListenerAdapter
import de.mtorials.dialphone.core.listeners.MessageListener
import de.mtorials.dialphone.core.sendTextMessage
import de.mtorials.dialphone.encyption.useEncryption
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
            addListeners(MessageListener(false) { event ->
                event.run { println("${message.author.userId} : ${message.body}") }
            })
            addListeners(ListenerAdapter {
                onRoomInvited { it.actions.join() }
            })
            //useEncryption()
            ktorLogLevel = LogLevel.ALL
        }
        val myListener = ListenerAdapter {
            onRoomInvited { event ->
                event.actions.join()
                event.room.sendTextMessage("Hey, I join everywhere!")
            }
            onRoomMessageReceived { event ->
                if (event.message.body == "ping") event answer "pong!"
            }
        }
//        val room = phone.createRoom("The Riders") {
//            makePublic()
//            topic = "please come and ride with me"
//        }

        println(phone.apiRequests.turnCredentials())

        println("Created room, start syncing...")
        phone.sync()
        while(true) {
            val msg = readln()
            if (msg == "exit") break
            if (msg == "!ping") phone.getJoinedRoomFutures().forEach {
                it.sendTextMessage("ping!")
            }
//            else room.sendTextMessage(msg)
            println()
        }
    }
}