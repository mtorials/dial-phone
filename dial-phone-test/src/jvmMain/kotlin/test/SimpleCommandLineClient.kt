package test

import de.mtorials.dialphone.api.DialPhoneApi
import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.listeners.ListenerAdapter
import de.mtorials.dialphone.core.listeners.MessageListener
import de.mtorials.dialphone.core.sendTextMessage
import io.ktor.client.features.logging.*
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        println("Starting....")
        DialPhoneApi("http://localhost:8008") {
            asUser("test", "test", true)
            ktorLogLevel = LogLevel.NONE
        }
        val phone = DialPhone("http://localhost:8008") {
            asUser("superman", "test", true)
            addListeners(MessageListener(false) { event ->
                event.run { println("${message.author.receive().displayName} : ${message.body}") }
            })
            addListeners(ListenerAdapter {
                onRoomInvited { it.invitedRoomActions.join() }
            })
            ktorLogLevel = LogLevel.NONE
        }
        val room = phone.createRoom("The Riders") {
            makePublic()
            topic = "please come and ride with me"
        }
        println("Created room, start syncing...")
        phone.sync()
        while(true) {
            val msg = readln()
            if (msg == "exit") break
            room.sendTextMessage(msg)
        }
    }
}