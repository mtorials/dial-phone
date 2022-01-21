package test

import de.mtorials.dialphone.core.DialPhone
import de.mtorials.dialphone.core.listeners.MessageListener
import de.mtorials.dialphone.core.sendTextMessage
import io.ktor.network.sockets.*
import kotlinx.coroutines.coroutineScope

suspend fun main() {
    DialPhone("https://localhost:8008") {
        asUser("test", "test", true)
    }
    val phone = DialPhone("http://localhost:8008") {
        asUser("superman", "test", true)
        addListeners(MessageListener { event ->
            event.run { println("${message.author.receive().displayName} : ${message.body}") }
        })
    }
    val room = phone.createRoom("The Riders") {
        makePublic()
        topic = "please come and ride with me"
    }
    while(true) {
        val msg = readln()
        if (msg == "exit") break
        room.sendTextMessage(msg)
    }
}