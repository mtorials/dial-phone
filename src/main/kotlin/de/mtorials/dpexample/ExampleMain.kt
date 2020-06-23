package de.mtorials.dpexample

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.mtorials.dialphone.DialPhone
import de.mtorials.dialphone.entities.Room
import de.mtorials.dpexample.cutstomevents.PositionEvent
import de.mtorials.dpexample.cutstomevents.TestStateEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

// Simple config Map
val config : Map<String, String> =
    jacksonObjectMapper().readValue(File("config.json").readText(Charsets.UTF_8))

val phone = DialPhone(
    homeServerURL = config["homeServerUrl"] ?: throw Error(),
    token = config["matrixToken"] ?: throw Error(),
    listeners = listOf(ExampleListener(), PositionListener(), StateListener(), MessageEventListener()),
    customEventTypes = arrayOf(PositionEvent::class, TestStateEvent::class)
)

fun main() {

    val job1 = GlobalScope.launch { phone.sync() }

    val myRoom : Room
    runBlocking {
        myRoom = (phone.getJoinedRoomFutureById("!YIqYutrrBUdGDombnI:mtorials.de")
            ?: throw RuntimeException("Not Found")).receive()
        myRoom.members.forEach { member -> println(member.displayName) }
        println(myRoom.avatarUrl)
        println(myRoom.name)
        val dn = phone.getUserByID("@mtorials:mtorials.de").displayName
        println(dn)
    }

    runBlocking {
        job1.join()
    }
}