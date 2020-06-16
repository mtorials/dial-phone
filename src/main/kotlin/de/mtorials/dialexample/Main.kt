package de.mtorials.dialexample

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.mtorials.dial.DialPhone
import de.mtorials.dial.entities.Room
import de.mtorials.dialexample.cutstomevents.PositionEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

// Simple config Map
val config : Map<String, String> =
    jacksonObjectMapper().readValue(File("config.json").readText(Charsets.UTF_8))

fun main() {

    val phone = DialPhone(
        homeServerURL = config["homeServerUrl"] ?: throw Error(),
        token = config["matrixToken"] ?: throw Error(),
        listeners = listOf(ExampleListener(), PositionListener()),
        customEventTypes = arrayOf(PositionEvent::class)
    )

    val job1 = GlobalScope.launch { phone.sync() }
    runBlocking {
        val myRoom : Room = (phone.getJoinedRoomFutureById("!YIqYutrrBUdGDombnI:mtorials.de")
            ?: throw RuntimeException("Not Found")).receive()
        myRoom.members.forEach { member -> println(member.displayName) }
        println(myRoom.avatarUrl)
        println(myRoom.name)
    }
    runBlocking {
        job1.join()
    }
}